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

import softwareinventario.softwareinventario.models.Cliente;
import softwareinventario.softwareinventario.service.IClienteService;

public class ClienteController {
    private static final String UPLOAD_DIR = "assets";

    public List<Cliente> listarClientes(IClienteService clienteService) {
        return clienteService.findAll();
    }

    public Cliente getById(Cliente cli, IClienteService clienteService) {
        return clienteService.findById(cli);
    }

    public void newCliente(Cliente cli, IClienteService clienteService) {
        clienteService.save(cli);
    }

    public void delete(Cliente cli, IClienteService clienteService) {
        clienteService.delete(cli);
    }

    public void update(Cliente cli, IClienteService clienteService) {
        clienteService.save(cli);
    }

    public void readDataFromExcel(Part file, IClienteService clienteService, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String filename = "";
            filename = getFilename(file);
            System.out.println(filename);
            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + UPLOAD_DIR + File.separator;

            File outputFilePath = new File(basePath + filename);
            System.out.println(outputFilePath);
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                Cliente cliente = new Cliente();
                cliente.setNombre(row.getCell(1).getStringCellValue());

                cliente.setApellido(row.getCell(2).getStringCellValue());

                cliente.setDireccion(row.getCell(3).getStringCellValue());

                cliente.setTel(row.getCell(4).getStringCellValue());

                cliente.setEmail(row.getCell(5).getStringCellValue());

                clienteService.save(cliente);
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

    private String getFilename(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
