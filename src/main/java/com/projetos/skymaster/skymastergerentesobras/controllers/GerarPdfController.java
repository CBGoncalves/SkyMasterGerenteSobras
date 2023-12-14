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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GerarPdfController {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void gerarPdfRelatorioEntradasSaidas(LocalDate de, LocalDate ate) throws DocumentException, IOException, ClassNotFoundException {

        String deText = de.toString();
        String ateText = ate.toString();

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

                Paragraph title = new Paragraph("Relatório de Entradas e Saídas", new
                        Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                String deFormatado = de.format(formatter);
                String ateFormatado = ate.format(formatter);

                Paragraph subtitle = new Paragraph("Referente ao período de "+ deFormatado + " a " + ateFormatado, new
                        Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                subtitle.setAlignment(Element.ALIGN_CENTER);

                doc.add(title);
                doc.add(subtitle);

                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(10);
                table.setWidthPercentage(100);

                // Defina a largura das colunas
                float[] columnWidths = {80f, 80f, 80f, 80f, 80f, 80f, 80f, 80f, 80f, 80f};
                table.setWidths(columnWidths);

                Paragraph paragrafoTipo = new Paragraph("Tipo de Registro", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoNF = new Paragraph("N° Nota Fiscal", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoQtd = new Paragraph("Quantidade", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoItem = new Paragraph("Tipo de Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDescItem = new Paragraph("Descrição do Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoMarca = new Paragraph("Marca", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoSetor = new Paragraph("Setor", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoObra = new Paragraph("Obra", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoUsuario = new Paragraph("Usuario", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDataRegis = new Paragraph("Data do Registro", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                PdfPCell tipoRegistro = new PdfPCell(paragrafoTipo);
                PdfPCell notaFiscal = new PdfPCell(paragrafoNF);
                PdfPCell quantidade = new PdfPCell(paragrafoQtd);
                PdfPCell tipoItem = new PdfPCell(paragrafoTipoItem);
                PdfPCell descricao = new PdfPCell(paragrafoDescItem);
                PdfPCell marca = new PdfPCell(paragrafoMarca);
                PdfPCell setor = new PdfPCell(paragrafoSetor);
                PdfPCell obra = new PdfPCell(paragrafoObra);
                PdfPCell usuario = new PdfPCell(paragrafoUsuario);
                PdfPCell dataRegis = new PdfPCell(paragrafoDataRegis);

                table.addCell(tipoRegistro);
                table.addCell(notaFiscal);
                table.addCell(quantidade);
                table.addCell(tipoItem);
                table.addCell(descricao);
                table.addCell(marca);
                table.addCell(setor);
                table.addCell(obra);
                table.addCell(usuario);
                table.addCell(dataRegis);

                table.setHeaderRows(1);

                try {
                    RelatorioDao relatorioDao = new RelatorioDao();
                    ResultSet rs = relatorioDao.getResultSet(deText, ateText);
                    System.out.println("Antes de adicionar linhas à tabela");
                    while (rs.next()) {
                        System.out.println("Tipo: " + rs.getString("tipo"));
                        System.out.println("Nota Fiscal: " + rs.getString("numNotaEntrada"));
                        table.addCell(new Phrase(rs.getString("tipo"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("numNotaEntrada"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("quantidade"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeTipoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("descricaoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeMarca"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeSetor"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeObra"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeUsuario"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        LocalDate data = rs.getDate("data").toLocalDate();
                        String dataFormatada = data.format(formatter);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(new com.itextpdf.text.Phrase(dataFormatada, new Font(Font.FontFamily.HELVETICA, 10f)));
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

    public void gerarPdfRelatorioEstoque() throws DocumentException, IOException, ClassNotFoundException {

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

                Paragraph title = new Paragraph("Relatório de Estoque", new
                        Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph subtitle = new Paragraph("Estoque atual de Itens", new
                        Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                subtitle.setAlignment(Element.ALIGN_CENTER);

                doc.add(title);
                doc.add(subtitle);

                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);

                // Defina a largura das colunas
                float[] columnWidths = {80f, 80f, 80f, 80f, 80f, 80f};
                table.setWidths(columnWidths);

                Paragraph paragrafoCodItem = new Paragraph("Código do Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoItem = new Paragraph("Tipo de Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDescITem = new Paragraph("Descrição do Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoMarca = new Paragraph("Marca", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoSetor = new Paragraph("Setor", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoQtd = new Paragraph("Quantidade", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                PdfPCell codITem = new PdfPCell(paragrafoCodItem);
                PdfPCell tipoItem = new PdfPCell(paragrafoTipoItem);
                PdfPCell descricaoItem = new PdfPCell(paragrafoDescITem);
                PdfPCell marca = new PdfPCell(paragrafoMarca);
                PdfPCell setor = new PdfPCell(paragrafoSetor);
                PdfPCell quantidade = new PdfPCell(paragrafoQtd);

                table.addCell(codITem);
                table.addCell(tipoItem);
                table.addCell(descricaoItem);
                table.addCell(marca);
                table.addCell(setor);
                table.addCell(quantidade);

                table.setHeaderRows(1);

                try {
                    RelatorioDao relatorioDao = new RelatorioDao();
                    ResultSet rs = relatorioDao.getItens();
                    System.out.println("Antes de adicionar linhas à tabela");
                    while (rs.next()) {
                        table.addCell(new Phrase(rs.getString("codItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeTipoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("descricaoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeMarca"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeSetor"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("quantidadeTotal"), new Font(Font.FontFamily.HELVETICA, 10f)));
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

    public void gerarPdfRelatorioReposicoes() throws DocumentException, IOException, ClassNotFoundException {

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

                Paragraph title = new Paragraph("Relatório de Reposições", new
                        Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph subtitle = new Paragraph("Esses são todas as reposições pendentes no estoque", new
                        Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                subtitle.setAlignment(Element.ALIGN_CENTER);

                doc.add(title);
                doc.add(subtitle);

                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(8);
                table.setWidthPercentage(100);

                // Defina a largura das colunas
                float[] columnWidths = {80f, 80f, 80f, 80f, 80f, 80f, 80f, 80f};
                table.setWidths(columnWidths);

                Paragraph paragrafoQuantidade = new Paragraph("Quantidade", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoItem = new Paragraph("Tipo de Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDescITem = new Paragraph("Descrição do Item", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoMarca = new Paragraph("Marca", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoSetor = new Paragraph("Setor", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoObra = new Paragraph("Obra", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoUsuario = new Paragraph("Usuário", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoData = new Paragraph("Data", new
                        Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);

                PdfPCell quantidade = new PdfPCell(paragrafoQuantidade);
                PdfPCell tipoItem = new PdfPCell(paragrafoTipoItem);
                PdfPCell descricaoItem = new PdfPCell(paragrafoDescITem);
                PdfPCell marca = new PdfPCell(paragrafoMarca);
                PdfPCell setor = new PdfPCell(paragrafoSetor);
                PdfPCell obra = new PdfPCell(paragrafoObra);
                PdfPCell usuario = new PdfPCell(paragrafoUsuario);
                PdfPCell data = new PdfPCell(paragrafoData);

                table.addCell(quantidade);
                table.addCell(tipoItem);
                table.addCell(descricaoItem);
                table.addCell(marca);
                table.addCell(setor);
                table.addCell(obra);
                table.addCell(usuario);
                table.addCell(data);

                table.setHeaderRows(1);

                try {
                    RelatorioDao relatorioDao = new RelatorioDao();
                    ResultSet rs = relatorioDao.getReposicoes();
                    System.out.println("Antes de adicionar linhas à tabela");
                    while (rs.next()) {
                        table.addCell(new Phrase(rs.getString("quantidade"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeTipoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("descricaoItem"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeMarca"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeSetor"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeObra"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        table.addCell(new Phrase(rs.getString("nomeUsuario"), new Font(Font.FontFamily.HELVETICA, 10f)));
                        LocalDate dataRs = rs.getDate("data").toLocalDate();
                        String dataFormatada = dataRs.format(formatter);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(new com.itextpdf.text.Phrase(dataFormatada, new Font(Font.FontFamily.HELVETICA, 10f)));
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
