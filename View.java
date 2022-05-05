import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class View extends JFrame {

	JPanel contentPane;
	private JTextField textField;

	private List<ClickableArticle> cts = new ArrayList<ClickableArticle>();
	private JLabel no_arts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		View dw = new View();
		dw.contentPane.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public View() {
		setTitle("Knowledge Repository");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 47, 211, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Article Name:");
		lblNewLabel.setBounds(10, 24, 77, 13);
		contentPane.add(lblNewLabel);
		
		JLabel textArea = new JLabel();
		textArea.setBounds(10, 104, 500, 500);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(237, 46, 85, 21);
		btnNewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TEST");
				DatabaseConnector c = DatabaseConnector.getInstance();
				c.connect();
				try {
					QueryResult q = c.executeSqlCommand("SELECT * FROM ARTICLE WHERE title ILIKE \'%" + textField.getText() + "%\'", QueryType.SELECT);
					System.out.println("Command executed");
					// TESTING
					List<Article> arts = new ArrayList<Article>();
					Article a = Article.fromResultSet(q.r);
					// contentPane.remove(no_arts);
					for (ClickableArticle ct : cts) {
						contentPane.remove(ct);
					}
					cts.clear();
					while(a != null)
					{
						arts.add(a);
						a = Article.fromResultSet(q.r);
					}
					arts.sort(new Comparator<Article>() {
						@Override
						public int compare(Article o1, Article o2) {
							return o1.author.username.toLowerCase().compareTo(o2.author.username.toLowerCase());
						}
						
					});
					for (int i = 0; i < arts.size(); i++)
					{
						Article article = arts.get(i);
						ClickableArticle t = new ClickableArticle(article);
						t.setBounds(50, textArea.getY() + 50*i, 500, 50);
						cts.add(t);
						contentPane.add(t);
					}
					// System.out.println("arts.size = " + arts.size());
					if(arts.size() == 0)
					{
						no_arts = new JLabel("No articles found!");
						no_arts.setBounds(50, textArea.getY(), 500, 50);
						contentPane.add(no_arts);
					}
					contentPane.updateUI();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}});
		contentPane.add(btnNewButton);
		
		
		JLabel lblNewLabel_1 = new JLabel("Content :");
		lblNewLabel_1.setBounds(10, 81, 66, 13);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setBounds(332, 46, 85, 21);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(true);
				Main v = new Main();
				v.main(null);
			}
		});
	}
}


class ClickableArticle extends JLabel
{
	String text;
	Article art;
	public ClickableArticle(Article a)
	{
		art = a;
		this.text = art.title + " by " + art.author.username;
		setText(text);
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("I have been clicked. My content is: " + text);
				JFrame f = new JFrame();
				f.setTitle(text);
				f.setBounds(100, 100, 300, 300);
				
				JLabel titlelabel = new JLabel(art.title, CENTER);
				titlelabel.setBounds(0, 0, 300, 50);

				JLabel contentlabel = new JLabel(processText(art.content));
				contentlabel.setBounds(0, 50, 300, 250);

				f.add(titlelabel);
				f.add(contentlabel);
				f.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// No implenetation needed
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// No implenetation needed
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// No implenetation needed
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// No implenetation needed
				
			}});
	}

	public static String processText(String txt) {
		txt = txt.replace("\n", "<br>");
		txt = "<html>" + txt + "</html>";
		return txt;
	}

}