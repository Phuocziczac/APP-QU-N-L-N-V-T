package helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExporter {

    public void exportDataToExcel(String[] headers, String[][] data, String directoryPath) {
        Workbook workbook = new XSSFWorkbook(); // Tạo một workbook Excel mới
        Sheet sheet = workbook.createSheet("Sheet1"); // Tạo một sheet mới có tên "Sheet1"

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]); // Đặt giá trị cho từng ô trong hàng tiêu đề
        }

        // Tạo các hàng dữ liệu
        for (int i = 0; i < data.length; i++) {
            Row dataRow = sheet.createRow(i + 1); // Dòng dữ liệu bắt đầu từ hàng 1
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(data[i][j]); // Đặt giá trị cho từng ô trong dòng dữ liệu
            }
        }

        // Tạo tên file duy nhất dựa trên thời gian hiện tại
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Results_" + timeStamp + ".xlsx";
        String filePath = directoryPath + File.separator + fileName;

        // Ghi workbook ra file
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Tạo tất cả các thư mục cha nếu chưa tồn tại
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut); // Ghi workbook vào file
                System.out.println("Excel file has been created successfully: " + filePath);

                // Mở file Excel ngay sau khi tạo
                File file = new File(filePath);
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi ghi file hoặc mở file
        } finally {
            try {
                workbook.close(); // Đóng workbook sau khi hoàn thành
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi đóng workbook
            }
        }
    }
}
