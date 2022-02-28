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
import softwareinventario.softwareinventario.models.ItemPedido;
import softwareinventario.softwareinventario.models.Pedido;
import softwareinventario.softwareinventario.models.Producto;
import softwareinventario.softwareinventario.models.Proveedor;
import softwareinventario.softwareinventario.service.IProductoService;

public class ProductoController {
    private static final String UPLOAD_DIR = "assets";

    public List<Producto> listaProductos(IProductoService productoService) {
        return productoService.findAll();
    }

    public Producto obtenerElMasVendido(IProductoService productoService) {
        List<Producto> productos = productoService.findTopSellers();
        Producto producto = productos.get(0);
        return producto;
    }

    public List<Producto> listaMasVendidos(IProductoService productoService) {
        return productoService.findTopSellers();
    }

    public Producto getById(Producto prod, IProductoService productoService) {
        return productoService.findById(prod);
    }

    public void newProduct(Producto prod, IProductoService productoService) {
        productoService.save(prod);
    }

    public void delete(Producto prod, IProductoService productoService) {
        productoService.delete(prod);
    }

    public void update(Producto prod, IProductoService productoService) {
        productoService.save(prod);
    }

    public void updateStock(Pedido pedido, IProductoService productoService) {
        for (ItemPedido itemPedido : pedido.getDetalle().getItems_pedidos()) {
            Producto producto = productoService.findById(itemPedido.getProducto());
            producto.setStock(producto.getStock() - itemPedido.getCantidad());
            producto.setTotalVendidos(producto.getTotalVendidos() + itemPedido.getCantidad());
            productoService.save(producto);
        }
    }

    public void newEntry(Producto productoEntry, IProductoService productoService) {
        Producto producto = productoService.findById(productoEntry);
        producto.setStock(producto.getStock() + productoEntry.getStock());
        productoService.save(producto);
    }

    public void readDataFromExcel(Part filePart, IProductoService productoService, HttpServletRequest request,
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
                Producto producto = new Producto();

                producto.setDescrip(row.getCell(1).getStringCellValue());

                producto.setStock((int) row.getCell(2).getNumericCellValue());

                producto.setPrecio(row.getCell(3).getNumericCellValue());

                producto.setStock((int) row.getCell(4).getNumericCellValue());

                Categoria categoria = new Categoria();
                categoria.setId((int) row.getCell(5).getNumericCellValue());
                producto.setCategoria(categoria);

                Proveedor proveedor = new Proveedor();
                proveedor.setId((int) row.getCell(6).getNumericCellValue());
                producto.setProveedor(proveedor);

                productoService.save(producto);
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
