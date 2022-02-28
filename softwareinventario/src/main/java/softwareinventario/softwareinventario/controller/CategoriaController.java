package softwareinventario.softwareinventario.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import softwareinventario.softwareinventario.models.Categoria;
import softwareinventario.softwareinventario.service.ICategoriaService;

public class CategoriaController {
    private static final String UPLOAD_DIR = "assets";

    public List<Categoria> listarCategorias(ICategoriaService categoriaService) {
        return categoriaService.findAll();
    }

    public Categoria getById(Categoria cat, ICategoriaService categoriaService) {
        return categoriaService.findById(cat);
    }

    public void newCategory(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.save(cat);
    }

    public void delete(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.delete(cat);
    }

    public void update(Categoria cat, ICategoriaService categoriaService) {
        categoriaService.save(cat);
    }

    public void readDataFromExcel(Part filePart, ICategoriaService categoriaService, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String filename = "";
            filename = getFilename(filePart);
            System.out.println(filename);
            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + UPLOAD_DIR + File.separator;

            File outputFilePath = new File(basePath + filename);
            System.out.println(outputFilePath);
            inputStream = filePart.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            XSSFWorkbook workbook = new XSSFWorkbook(filePart.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                Categoria categoria = new Categoria();

                categoria.setDescripcion(row.getCell(1).getStringCellValue());

                categoriaService.save(categoria);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private String getFilename(Part filePart) {
        for (String content : filePart.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
