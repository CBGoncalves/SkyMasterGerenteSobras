package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projetos.skymaster.skymastergerentesobras.dao.RelatorioDao;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GerarPdfController {

    public void gerarPdfRelatorio(String de, String ate) throws DocumentException, IOException, ClassNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Document doc = new Document(PageSize.A4.rotate(),0,0,0,0);

            try{
                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));

                doc.open();

                Image logo = Image.getInstance("src/main/resources/com/projetos/skymaster/skymastergerentesobras/img/logo_sky.jpg");
                logo.scalePercent(20);
                logo.setAlignment(Element.ALIGN_CENTER);
                doc.add(logo);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                Paragraph title = new Paragraph("Relatório de Entradas e Saídas", new
                        Font(Font.FontFamily.COURIER, 18, Font.BOLD, new BaseColor(0, 0, 0)));
                title.setAlignment(Element.ALIGN_CENTER);
                doc.add(title);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(10);
                table.setWidthPercentage(100);

                // Defina a largura das colunas
                float[] columnWidths = {50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f};
                table.setWidths(columnWidths);

                Paragraph paragrafoTipo = new Paragraph("Tipo de Registro", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoNF = new Paragraph("N° Nota Fiscal", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoQtd = new Paragraph("Quantidade", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoItem = new Paragraph("Tipo de Item", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDescItem = new Paragraph("Descrição do Item", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoMarca = new Paragraph("Marca", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoObra = new Paragraph("Obra", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoUsuario = new Paragraph("Usuario", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoUsuario = new Paragraph("Tipo do Usuário", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDataRegis = new Paragraph("Data do Registro", new
                        Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                PdfPCell tipoRegistro = new PdfPCell(paragrafoTipo);
                PdfPCell notaFiscal = new PdfPCell(paragrafoNF);
                PdfPCell quantidade = new PdfPCell(paragrafoQtd);
                PdfPCell tipoItem = new PdfPCell(paragrafoTipoItem);
                PdfPCell descricao = new PdfPCell(paragrafoDescItem);
                PdfPCell marca = new PdfPCell(paragrafoMarca);
                PdfPCell obra = new PdfPCell(paragrafoObra);
                PdfPCell usuario = new PdfPCell(paragrafoUsuario);
                PdfPCell tipoUsuario = new PdfPCell(paragrafoTipoUsuario);
                PdfPCell dataRegis = new PdfPCell(paragrafoDataRegis);

                table.addCell(tipoRegistro);
                table.addCell(notaFiscal);
                table.addCell(quantidade);
                table.addCell(tipoItem);
                table.addCell(descricao);
                table.addCell(marca);
                table.addCell(obra);
                table.addCell(usuario);
                table.addCell(tipoUsuario);
                table.addCell(dataRegis);

                table.setHeaderRows(1);

                try {
                    RelatorioDao relatorioDao = new RelatorioDao();
                    ResultSet rs = relatorioDao.getResultSet(de, ate);
                    System.out.println("Antes de adicionar linhas à tabela");
                    while (rs.next()) {
                        System.out.println("Tipo: " + rs.getString("tipo"));
                        System.out.println("Nota Fiscal: " + rs.getString("numNotaEntrada"));
                        table.addCell(rs.getString("tipo"));
                        table.addCell(rs.getString("numNotaEntrada"));
                        table.addCell(rs.getString("quantidade"));
                        table.addCell(rs.getString("nomeTipoItem"));
                        table.addCell(rs.getString("descricaoItem"));
                        table.addCell(rs.getString("nomeMarca"));
                        table.addCell(rs.getString("nomeObra"));
                        table.addCell(rs.getString("nomeUsuario"));
                        table.addCell(rs.getString("nomeTipoUsuario"));
                        java.sql.Date data = rs.getDate("data");
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(new com.itextpdf.text.Phrase(data.toString()));
                        table.addCell(cell);
                    }
                    System.out.println("Depois de adicionar linhas à tabela");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                doc.add(table);
                doc.close();
                writer.close();

                System.out.println("PDF criado!");
                showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                        "Relatório criado com sucesso!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Atenção!",
                    "Ação cancelada pelo usuário");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
