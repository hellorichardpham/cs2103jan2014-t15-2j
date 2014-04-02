import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.*;

public class Email {

	private Scanner input;
	@SuppressWarnings("unused")
	private ArrayList<Task> taskList;

	public Email(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public void emailUser() throws Exception {

		String name = "TaskTracker";
		String email = "tasktrackernus@gmail.com ";
		String password = "112233445566778899NUS";
		String recipient;

		// default host
		String host = "smtp.gmail.com";

		input = new Scanner(System.in);
		System.out.println("Please enter recipient email: ");
		recipient = input.nextLine();

		if (email.contains("gmail.com")) {
			host = "smtp.gmail.com";
		} else if (email.contains("hotmail.com") || email.contains("live.com")) {
			host = "smtp.live.com";
		}

		String[] recipients = new String[] { recipient };
		String subject = "Storage.txt";

		String messageBody;

		BufferedReader br = new BufferedReader(new FileReader("Storage.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}

		messageBody = sb.toString();
		br.close();

		String storageName = "Storage.txt";
		String pdfName = "Storage.pdf";

		taskList = ExeCom.getTaskListInstance();

		System.out.println("Sending email. Please wait.");
		if (new MailUtil().sendMail(recipients, subject, messageBody, name, email, password, host))
		System.out.println("Email sent. Thank you for waiting.");

		try {
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createPdf(String filename)
	        throws IOException, DocumentException {
	    	// step 1
	        Document document = new Document();
	        // step 2
	        PdfWriter.getInstance(document, new FileOutputStream(filename));
	        // step 3
	        document.open();
	        // step 4
	        PdfPTable table = createTable1();
	        document.add(table);
	        table = createTable2();
	        table.setSpacingBefore(5);
	        table.setSpacingAfter(5);
	        document.add(table);
	        // step 5
	        document.close();
	    }
	
	public static PdfPTable createTable1() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(288 / 5.23f);
        table.setWidths(new int[]{2, 1, 1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 1"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
	
	public static PdfPTable createTable2() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(288);
        table.setLockedWidth(true);
        table.setWidths(new float[]{2, 1, 1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 2"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
	
}
