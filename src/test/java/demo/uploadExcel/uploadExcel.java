package demo.uploadExcel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//上传Excel文件
public class uploadExcel {


    @RequestMapping(path = {"/whitelist"}, method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String whitelist(@RequestParam("file") MultipartFile file, @RequestParam("campaignId") Long campaignId) throws IOException {


        String fileName = file.getOriginalFilename();

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return "上传文件格式不正确";
        }

        // 获取工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        // 获取工作页
        Sheet sheet = wb.getSheetAt(0);

        if (sheet == null) {
            return "上传文件内容有误";
        }

        // 传入参数为空列表
        List<Integer> paramErrorList = new ArrayList<>();

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            // 为null判断
            if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null) {
                paramErrorList.add(r + 1);
                continue;
            }
            // 设置Cell类型
            row.getCell(0).setCellType(CellType.STRING);
            row.getCell(1).setCellType(CellType.STRING);
            row.getCell(2).setCellType(CellType.STRING);
            // 为空判断
            if (row.getCell(0).equals("") || row.getCell(1).equals("") || row.getCell(2).equals("")) {
                paramErrorList.add(r + 1);
                continue;
            }

            //获取cell
            Long l1 = Long.valueOf(row.getCell(0).getStringCellValue());


        }
        return "sucess";
    }

}
