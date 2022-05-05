
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class Publish {

	public JFrame mainwindow;
	public JLabel heading;
	
	public TextAndField titlefield;
	public TextAndField author;
	public TextAndField email;
	public TextAndField topic;
	public ContentArea content;
	
	public JButton submitbtn;
	private JButton btnNewButton;
	
	
	public Publish(String title)
	{
		mainwindow = new JFrame();
		mainwindow.setSize(800, 500);
		mainwindow.setTitle("Knowledge Repository");
		mainwindow.getContentPane().setLayout(null);
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		heading = new JLabel("Publish Article");
		heading.setBounds(0, 0, 800, 50);
		heading.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 25) );
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		mainwindow.getContentPane().add(heading);
		
		titlefield = new TextAndField("Title: ", 10, 30, 80, 20, 200, 20);
		titlefield.addToWindow(mainwindow);
		
		author = new TextAndField("Author: ", 10, 80, 80, 20, 200, 20);
		author.addToWindow(mainwindow);
		
		topic = new TextAndField("Topic: ", 10, 120, 80, 20, 200, 20);
		topic.addToWindow(mainwindow);

		email = new TextAndField("Email: ", 10, 150, 80, 20, 200, 20);
		email.addToWindow(mainwindow);
		
		content = new ContentArea("Content: ", 10, 180, 80, 20, 200, 200);
		content.addToWindow(mainwindow);
		
		submitbtn = new JButton("Publish Article");
		submitbtn.setBounds(100, 400, 120, 20);
		submitbtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TEST");
				DatabaseConnector c = DatabaseConnector.getInstance();
				c.connect();
				try {
					// QueryResult q = c.executeSqlCommand("SELECT content FROM ARTICLE WHERE title=\'" + textField.getText() + "\'", QueryType.SELECT);
					System.out.println("Command executed");
					// title|author|topic|content| article_id |author_email
					String title = titlefield.textedit.getText();
					String authorname = author.textedit.getText();
					String topicname = topic.textedit.getText();
					String articlecontent = content.textedit.getText();
					String emailstr = email.textedit.getText();
					System.out.println("User: " + authorname + "has written a new article");
					Article newarticle = new Article(title, emailstr, topicname, articlecontent);
					newarticle.addToDB();

					// if(q.r.next())
					// {
					// 	// String content = q.r.getString("content");
					// 	// System.out.println("Content is: " + content);
					// 	// textArea.setText(content);
					// }
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				titlefield.textedit.setText("");
				author.textedit.setText("");
				topic.textedit.setText("");
				content.textedit.setText("");
				email.textedit.setText("");
			}});
		mainwindow.getContentPane().add(submitbtn);
		
		btnNewButton = new JButton("Back");
		btnNewButton.setBounds(303, 400, 85, 21);
		mainwindow.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainwindow.setVisible(false);
				Main v = new Main();
				v.main(null);
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		
		Publish dw = new Publish("Knowledge Repo");
		dw.mainwindow.setVisible(true);
	}

}


class TextAndField
{
	public JLabel label;
	public JTextField textedit;
	
	public TextAndField(String labelname, int left, int top, int labelwidth, int labelheight,int fieldwidth, int fieldheight)
	{
		label = new JLabel(labelname);
		label.setBounds(left, top, labelwidth, labelheight);
		
		textedit = new JTextField();
		textedit.setBounds(left + labelwidth + 2,  top, fieldwidth, fieldheight);
	}
	
	public void addToWindow(JFrame window)
	{
		window.add(label);
		window.add(textedit);
	}
}

class ContentArea
{
	public JLabel label;
	public JTextArea textedit;
	
	public ContentArea(String labelname, int left, int top, int labelwidth, int labelheight,int fieldwidth, int fieldheight)
	{
		label = new JLabel(labelname);
		label.setBounds(left, top, labelwidth, labelheight);
		
		textedit = new JTextArea();
		textedit.setBounds(left + labelwidth + 2,  top, fieldwidth, fieldheight);
	}
	
	public void addToWindow(JFrame window)
	{
		window.add(label);
		window.add(textedit);
	}
}