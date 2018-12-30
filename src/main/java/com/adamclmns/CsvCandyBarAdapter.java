package com.adamclmns;

import com.adamclmns.data.CandyBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class CsvCandyBarAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(CsvCandyBarAdapter.class);
    private String filename;

    public CsvCandyBarAdapter(String csvFileName) {
        this.filename = csvFileName;
    }

    private static CandyBar candyBarFromExcelRow(String rowString, List<String> headers) {

        List<String> row = parseExcelCSVRow(rowString);
        //LOG.warn("Parse List to CandyBar "+row);
        // List maintains order. Headers were added in same order as the list elements
        CandyBar thisBar = new CandyBar();
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i).replaceAll(" ", "");
            if (i == 0) {
                //LOG.warn("I is ZERO - header ::"+header+":: the value? "+header.equalsIgnoreCase("Name"));
            }
            if (i == 0)
                thisBar.setName(row.get(i));
            else if (header.equalsIgnoreCase("Manufacturer"))
                thisBar.setManufacturer(row.get(i));
            else if (header.equalsIgnoreCase("Distribution"))
                thisBar.setDistribution(Arrays.asList(row.get(i).split(",")));
            else if (header.equalsIgnoreCase("Description")) {
                if (i < row.size())
                    thisBar.setDescription(row.get(i));
            }

        }
        //LOG.warn(thisBar.toString());
        return thisBar;
    }

    private static List<String> parseExcelCSVRow(String rowString) {
        List<String> splitRow = new ArrayList<>();
        // Scan the entire row by character.
        char delimiter = ',';
        char encapsulator = '"';
        boolean encapsulated = false;
        char[] characters = rowString.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] == delimiter && !encapsulated) {
                splitRow.add(sb.toString().trim());
                sb = new StringBuilder();
            } else if (characters[i] == encapsulator) {
                encapsulated = !encapsulated;
                sb.append(characters[i]);
            } else {
                sb.append(characters[i]);
            }
        }
        splitRow.add(sb.toString().trim());
        LOG.warn("PARSED ::" + splitRow);
        return splitRow;
    }

    public List<CandyBar> parseCSVToCandyBarList() {
        List<CandyBar> candyBars = new ArrayList<CandyBar>();
        List<String> fieldNames = new ArrayList<>();
        try (FileReader fr = new FileReader(filename); Scanner scanner = new Scanner(fr)) {
            // Establish the Headers
            if (scanner.hasNext()) {
                String[] rawHeaders = (scanner.nextLine().split(","));
                for (String header : rawHeaders) {
                    fieldNames.add(header.trim());
                }
                LOG.warn("Headers : " + fieldNames);

            }
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                candyBars.add(candyBarFromExcelRow(row, fieldNames));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candyBars;
    }
}
