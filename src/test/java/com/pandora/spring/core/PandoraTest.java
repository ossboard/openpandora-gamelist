package com.pandora.spring.core;

import com.pandora.spring.model.GameList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PandoraTest {


    @Test
    public void xml2xls() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/gamelist.xml");
        String body = IOUtils.toString(inputStream, "UTF-8");
        JAXBContext jaxbContext = JAXBContext.newInstance(GameList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        GameList gameList = (GameList) unmarshaller.unmarshal(new StringReader(body));

        URL url = this.getClass().getResource("/excel.xls");
        String XLS_TEMPLATE = url.getPath();
        InputStream is = new FileInputStream(XLS_TEMPLATE);
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet =  wb.getSheetAt(0);
        int start = 1;
        List<GameList.Game> games = gameList.getGame();
        for(int i = 0; i < games.size() ; i++ ) {
            GameList.Game game = games.get(i);
            sheet.createRow(start + i);
            sheet.getRow(start + i).createCell(0).setCellValue(game.getId());
            sheet.getRow(start + i).createCell(1).setCellValue(game.getSource());
            sheet.getRow(start + i).createCell(2).setCellValue(game.getPath());
            sheet.getRow(start + i).createCell(3).setCellValue(game.getName());
            sheet.getRow(start + i).createCell(4).setCellValue(game.getDesc());
            sheet.getRow(start + i).createCell(5).setCellValue(game.getImage());
            sheet.getRow(start + i).createCell(6).setCellValue(game.getMarquee());
            sheet.getRow(start + i).createCell(7).setCellValue(game.getVideo());
            sheet.getRow(start + i).createCell(8).setCellValue(game.getThumbnail());
            sheet.getRow(start + i).createCell(9).setCellValue(game.getRating());
            sheet.getRow(start + i).createCell(10).setCellValue(game.getReleasedate());
            sheet.getRow(start + i).createCell(11).setCellValue(game.getDeveloper());
            sheet.getRow(start + i).createCell(12).setCellValue(game.getPublisher());
            sheet.getRow(start + i).createCell(13).setCellValue(game.getGenre());
            sheet.getRow(start + i).createCell(14).setCellValue(game.getPlayers());
        }
        is.close();
        FileOutputStream out = new FileOutputStream("./test.xls");
        wb.write(out);
        out.flush();
        out.close();
    }



    @Test
    public void xls2xml() throws Exception {
        URL url = this.getClass().getResource("/demo.xls");
        String XLS_TEMPLATE = url.getPath();
        InputStream is = new FileInputStream(XLS_TEMPLATE);
        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet =  wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();

        GameList gameList = new GameList();
        GameList.Game game = new GameList.Game();
        List<GameList.Game> games = new ArrayList<>();
        int start = 1;
        for(int i = 0; i < rows ; i++) {
            game = new GameList.Game();
            game.setId(sheet.getRow(start + i).getCell(0).getStringCellValue());
            game.setSource(sheet.getRow(start + i).getCell(1).getStringCellValue());
            game.setPath(sheet.getRow(start + i).getCell(2).getStringCellValue());
            game.setName(sheet.getRow(start + i).getCell(3).getStringCellValue());
            game.setDesc(sheet.getRow(start + i).getCell(4).getStringCellValue());
            game.setImage(sheet.getRow(start + i).getCell(5).getStringCellValue());
            game.setMarquee(sheet.getRow(start + i).getCell(6).getStringCellValue());
            game.setVideo(sheet.getRow(start + i).getCell(7).getStringCellValue());
            game.setThumbnail(sheet.getRow(start + i).getCell(8).getStringCellValue());
            game.setRating(sheet.getRow(start + i).getCell(9).getStringCellValue());
            game.setReleasedate(sheet.getRow(start + i).getCell(10).getStringCellValue());
            game.setDeveloper(sheet.getRow(start + i).getCell(11).getStringCellValue());
            game.setPublisher(sheet.getRow(start + i).getCell(12).getStringCellValue());
            game.setGenre(sheet.getRow(start + i).getCell(13).getStringCellValue());
            game.setPlayers(sheet.getRow(start + i).getCell(14).getStringCellValue());
            games.add(game);
        }
        gameList.setGame(games);


        JAXBContext jaxbContext = JAXBContext.newInstance(GameList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(gameList, stringWriter);
        String xml = StringUtils.remove(stringWriter.toString(), " encoding=\"UTF-8\" standalone=\"yes\"");
        File file = new File( "out.xml" );
        FileUtils.writeStringToFile(file, xml, "UTF-8");




    }


}
