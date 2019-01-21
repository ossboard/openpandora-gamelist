package com.pandora.spring.agent;

import com.pandora.spring.model.GameList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertCore implements CommandLineRunner {


    public void xml2xls(String xmlFile, String xlsFile) throws Exception {

        File in = new File(xmlFile);
        if(!in.exists()) {
            throw new Exception(" File Not Found (" + xmlFile + ")");
        }
        InputStream inputStream = new FileInputStream(in);
        String body = IOUtils.toString(inputStream, "UTF-8");
        JAXBContext jaxbContext = JAXBContext.newInstance(GameList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        GameList gameList = (GameList) unmarshaller.unmarshal(new StringReader(body));

        Resource resource = new ClassPathResource("excel.xls");
        InputStream is = resource.getInputStream();
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
        FileOutputStream out = new FileOutputStream(xlsFile);
        wb.write(out);
        out.flush();
        out.close();
    }

    public void xls2xml(String xlsFile, String xmlFile) throws Exception {
        File in = new File(xlsFile);
        if(!in.exists()) {
            throw new Exception(" File Not Found (" + xlsFile + ")");
        }
        String XLS_TEMPLATE = in.getAbsolutePath();
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
        File file = new File(xmlFile);
        FileUtils.writeStringToFile(file, xml, "UTF-8");
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if( StringUtils.endsWith(args[0],"xml") && StringUtils.endsWith(args[1],"xls")) {
                xml2xls(args[0],args[1]);
                System.out.println(args[1] + " Convert OK");
                // xls -> xml
            } else if( StringUtils.endsWith(args[0],"xls") && StringUtils.endsWith(args[1],"xml")) {
                xls2xml(args[0],args[1]);
                System.out.println(args[1] + " Convert OK");
            } else {
                System.out.println("예제) gamelist.xml 파일을 out.xls 파일로 만들때");
                System.out.println("> java -jar openpandora-gamelist.jar gamelist.xml out.xls");
                System.out.println("예제) out.xls 파일을 gamelist2.xml 파일로 만들때");
                System.out.println("> java -jar openpandora-gamelist.jar out.xls gamelist2.xml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
