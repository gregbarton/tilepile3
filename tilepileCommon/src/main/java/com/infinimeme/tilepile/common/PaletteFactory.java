package com.infinimeme.tilepile.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 **/
public class PaletteFactory {

	// ~ Static fields/initializers
	// *****************************************************************

	/** DOCUMENT ME! */
	public static final String BACKUP_EXTENSION = ".tpp";

	// ~ Methods
	// ************************************************************************************

	/**
	 * DOCUMENT ME!
	 * 
	 * @param paletteName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 **/
	public static final Palette make(String paletteName) {

		return new Palette(paletteName);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param paletteName
	 *            DOCUMENT ME!
	 * @param numColors
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws IllegalStateException
	 *             DOCUMENT ME!
	 **/
	public static final Palette make(String paletteName, int numColors) {

		List<Palette.PaletteColor> colors = new LinkedList<Palette.PaletteColor>();

		colors.add(new Palette.PaletteColor(0, "000_Color", new Color((float) 1.0, (float) 1.0, (float) 1.0)));

		float inverseRoot = (float) (1.0 / Math.pow(numColors, 1 / 3.0));

		float red = 0.0f;
		float green = 0.0f;
		float blue = 0.0f;

		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		numberFormat.setMinimumIntegerDigits(3);

		for (int i = 1; i < numColors; i++) {

			colors.add(new Palette.PaletteColor(i, numberFormat.format(i) + "_Color", new Color(red, green, blue)));

			red += inverseRoot;

			if (red >= 1.0) {
				red -= 1.0;
				green += inverseRoot;
			}

			if (green >= 1.0) {
				green -= 1.0;
				blue += inverseRoot;
			}

			if (blue >= 1.0) {
				throw new IllegalStateException("OVERFLOW!");
			}
		}

		return new Palette(paletteName, colors);
	}

	/**
	 * Make a palette from an existing index color image
	 * 
	 * @param paletteName
	 *            Name of the new palette
	 * @param image
	 *            Index color image
	 * 
	 * @return New palette
	 * 
	 * @throws TilepileException
	 *             If the input image does not have an index color model
	 **/
	public static final Palette make(String paletteName, BufferedImage image) throws TilepileException {

		IndexColorModel imageCM = null;

		try {
			imageCM = (IndexColorModel) image.getColorModel();
		} catch (ClassCastException cce) {
			throw new TilepileException("Input image does not have index color model! It has "
				+ image.getColorModel().getClass(), cce);
		}

		int[] imageColors = new int[imageCM.getMapSize()];
		imageCM.getRGBs(imageColors);

		int numColors = imageColors.length;

		// search color map backwards until we get a non-black color
		for (int i = imageColors.length; i > 0 && imageColors[i - 1] == 0xff000000; i--) {
			numColors = i;
		}

		List<Palette.PaletteColor> colors = new LinkedList<Palette.PaletteColor>();

		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		numberFormat.setMinimumIntegerDigits(3);

		for (int i = 0; i < numColors; i++) {
			colors.add(new Palette.PaletteColor(i, numberFormat.format(i) + "_Color", new Color(imageColors[i])));
		}

		return new Palette(paletteName, colors);
	}

	/**
	 * Create a palette from a tilepile palette backup file (tpp)
	 * 
	 * @param paletteFile
	 *            Name of a tilepile palette backup file (tpp)
	 * 
	 * @return Produced Palette object
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 **/
	public static final Palette make(File paletteFile) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(paletteFile));

		String name = br.readLine().trim();
		/* int numColors = */Integer.parseInt(br.readLine().trim());

		List<Palette.PaletteColor> colors = new LinkedList<Palette.PaletteColor>();

		int index = 0;
		String line = null;

		while (((line = br.readLine()) != null) && !"".equals(line)) {

			String[] data = line.split("\t");

			Color color = new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));

			Palette.PaletteColor pc = new Palette.PaletteColor(index, data[4], color);

			colors.add(pc);

			// TODO: handle non constructor data

			if (data.length > 5) {

				pc.setIndex(Integer.parseInt(data[5]));
				pc.setCountInStock(Integer.parseInt(data[6]));
				pc.setSubstitution(data[7]);
				pc.setKilosInDesign(Double.parseDouble(data[8]));
				pc.setKilosInStock(Double.parseDouble(data[9]));
				pc.setKilosOnOrder(Double.parseDouble(data[10]));

			}

			index++;
		}

		br.close();

		return new Palette(name, colors);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param paletteName
	 *            DOCUMENT ME!
	 * @param paletteFile
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 **/
	public static final Palette makeFromPhotoshopPalette(String paletteName, File paletteFile) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(paletteFile));
		List<Palette.PaletteColor> colors = new LinkedList<Palette.PaletteColor>();

		int index = 0;
		String line = null;

		while (((line = br.readLine()) != null) && !"".equals(line)) {

			String[] fields = line.split(" +", 6);

			if (fields.length >= 6) {
				colors.add(new Palette.PaletteColor(index, fields[5].replaceFirst("1STATE_", ""), new Color(Integer
					.parseInt(fields[1]), Integer.parseInt(fields[2]), Integer.parseInt(fields[3]))));
			}

			index++;
		}

		return new Palette(paletteName, colors);
	}

	/**
	 * Create a new palette from several source palettes.
	 * 
	 * @param paletteName
	 * @param mergeNames
	 * @param mergeColors
	 * @param palettes
	 * @return
	 */
	public static final Palette make(String paletteName, boolean mergeNames, boolean mergeColors,
		Collection<Palette> palettes) {

		Collection<Palette.PaletteColor> colors = null;

		if (mergeNames && mergeColors) {
			Map<String, Palette.PaletteColor> map = new HashMap<String, Palette.PaletteColor>();
			for (Palette p : palettes) {
				map.putAll(p.getNameMap());
			}
			Palette tmp = new Palette(paletteName, new ArrayList<Palette.PaletteColor>(map.values()));
			colors = tmp.getColorMap().values();
		} else if (mergeNames) {
			Map<String, Palette.PaletteColor> map = new HashMap<String, Palette.PaletteColor>();
			for (Palette p : palettes) {
				map.putAll(p.getNameMap());
			}
			colors = map.values();
		} else if (mergeColors) {
			Map<Color, Palette.PaletteColor> map = new HashMap<Color, Palette.PaletteColor>();
			for (Palette p : palettes) {
				map.putAll(p.getColorMap());
			}
			colors = map.values();
		} else {
			colors = new LinkedList<Palette.PaletteColor>();
			for (Palette p : palettes) {
				System.out.println("Merging palette " + p.getName() + " with " + p.getColors().values().size()
					+ " colors");
				for (Palette.PaletteColor color : p.getColors().values()) {
					colors.add(color);
				}
			}
		}

		return new Palette(paletteName, new ArrayList<Palette.PaletteColor>(colors));
	}

	public static final Palette makeFromSpreadsheet(String paletteName, File file) throws IOException {

		Workbook wb = new XSSFWorkbook(new FileInputStream(file));

		Sheet sheet = wb.getSheetAt(0);

		List<Palette.PaletteColor> colors = new LinkedList<Palette.PaletteColor>();

		System.out.println("NUM ROWS: " + sheet.getLastRowNum());

		Iterator<Row> rit = sheet.rowIterator();

		Row first = rit.next();
		int r = 0;
		outer: while (rit.hasNext()) {
			r++;
			Row row = rit.next();

			// Check the first four columns. If one is blank, that's the last
			// color.
			for (int i = 0; i < 4; i++) {
				if (row.getCell(i) == null) {
					System.out.println("BAILING AT ROW " + r + " CELL " + i);
					break outer;
				}
			}
			System.out.println("AT ROW " + r);
			Cell nameCell = row.getCell(0);
			Cell colorCell = row.getCell(1);
			Cell indexCell = row.getCell(2);
			Cell countInStockCell = row.getCell(3);
			Cell substitutionCell = row.getCell(4);
			Cell kilosInStockCell = row.getCell(5);
			Cell kilosOnOrderCell = row.getCell(6);

			int index = 0;
			if (indexCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				index = (int) indexCell.getNumericCellValue();
			} else {
				index = Integer.parseInt(indexCell.getStringCellValue());
			}

			int colorNumber = 0;
			if (colorCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				colorNumber = (int) colorCell.getNumericCellValue();
			} else {
				colorNumber = Integer.parseInt(colorCell.getStringCellValue().toUpperCase(), 16);
			}

			Palette.PaletteColor color = new Palette.PaletteColor(index, nameCell.getStringCellValue(), new Color(colorNumber));

			if (substitutionCell != null) {
				color.setSubstitution(substitutionCell.getStringCellValue());
			}
			if (countInStockCell != null) {
				if (indexCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					color.setCountInStock((int) countInStockCell.getNumericCellValue());
				} else {
					color.setCountInStock(Integer.parseInt(countInStockCell.getStringCellValue()));
				}
			}
			if (kilosInStockCell != null) {
				if (kilosInStockCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					color.setKilosInStock(kilosInStockCell.getNumericCellValue());
				} else {
					color.setKilosInStock(Double.parseDouble(kilosInStockCell.getStringCellValue()));
				}
			}
			if (kilosOnOrderCell != null) {
				if (kilosOnOrderCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					color.setKilosOnOrder(kilosOnOrderCell.getNumericCellValue());
				} else {
					color.setKilosInStock(Double.parseDouble(kilosOnOrderCell.getStringCellValue()));
				}
			}

			colors.add(color);
		}

		return new Palette(paletteName, colors);

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param palette
	 *            DOCUMENT ME!
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 * @throws TilepileException
	 *             DOCUMENT ME!
	 **/

	public static final void saveAsBackup(Palette palette) throws IOException, TilepileException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(palette.getName() + BACKUP_EXTENSION)));

		bw.write(palette.getName());
		bw.newLine();

		Collection<Palette.PaletteColor> colors = palette.getColors().values();

		bw.write(Integer.toString(colors.size()));
		bw.newLine();

		for (Palette.PaletteColor pc : colors) {

			bw.write(Integer.toString(pc.getColor().getRed()));
			bw.write("\t");
			bw.write(Integer.toString(pc.getColor().getGreen()));
			bw.write("\t");
			bw.write(Integer.toString(pc.getColor().getBlue()));
			bw.write("\t");
			String rgb = Integer.toHexString(pc.getColor().getRGB());
			bw.write(rgb.substring(2, rgb.length()));
			bw.write("\t");
			bw.write(pc.getName());
			bw.write("\t");
			bw.write(Integer.toString(pc.getIndex()));
			bw.write("\t");
			bw.write(Integer.toString(pc.getCountInStock()));
			bw.write("\t");
			bw.write(pc.getSubstitution());
			bw.write("\t");
			bw.write(Double.toString(pc.getKilosInDesign()));
			bw.write("\t");
			bw.write(Double.toString(pc.getKilosInStock()));
			bw.write("\t");
			bw.write(Double.toString(pc.getKilosOnOrder()));
			bw.newLine();
		}

		bw.flush();
		bw.close();
	}

	public static final void saveAsSpreadsheet(Palette palette) throws IOException, TilepileException {

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Tiles");

		Row firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Name");
		firstRow.createCell(1).setCellValue("Color");
		firstRow.createCell(2).setCellValue("Index");
		firstRow.createCell(3).setCellValue("CountInStock");
		firstRow.createCell(4).setCellValue("Substitution");
		firstRow.createCell(5).setCellValue("KilosInStock");
		firstRow.createCell(6).setCellValue("KilosOnOrder");

		for (Palette.PaletteColor pc : palette.getColors().values()) {
			Row row = sheet.createRow(pc.getIndex() + 1);
			row.createCell(0).setCellValue(pc.getName());
			Cell colorCell = row.createCell(1);
			colorCell.setCellValue(Integer.toHexString(pc.getColor().getRGB()).substring(2));
			XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
			style.setFillForegroundColor(new XSSFColor(pc.getColor()));
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			colorCell.setCellStyle(style);
			row.createCell(2).setCellValue(pc.getIndex());
			row.createCell(3).setCellValue(pc.getCountInStock());
			row.createCell(4).setCellValue(pc.getSubstitution());
			row.createCell(5).setCellValue(pc.getKilosInStock());
			row.createCell(6).setCellValue(pc.getKilosOnOrder());
		}

		FileOutputStream fos = new FileOutputStream(palette.getName() + ".xlsx");

		wb.write(fos);

		fos.flush();
		fos.close();
	}

}
