package Lince;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class JaguarUI extends JFrame {
    public JaguarUI() {

        //Acessa o diretório principal (Home)
        String userHome = System.getProperty("user.home");
        File file = new File("/home/groundzero/Documents");

        if(!file.exists()){
            System.out.println("Directory not found" + userHome);
            return;
        }else{
            System.out.println("Directory accessed: " + userHome);
        }

        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Nothing found in: " + userHome);
        }else{
            System.out.println("Files found in userhome: " + userHome);
            for(File f : files){
                System.out.println(f.getName());
            }
        }

        //Ajustes da janela do Programa
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Jaguar");
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);

        JToolBar jToolBar = new JToolBar();

        JButton nextButton = new JButton("Next");
        JButton deleteButton = new JButton("Delete");

        jToolBar.add(nextButton);
        jToolBar.add(deleteButton);

        Container pane = this.getContentPane();
        pane.add(jToolBar, BorderLayout.NORTH);

        //Root Node da JTree
        DefaultMutableTreeNode treeNodes = new DefaultMutableTreeNode(file.getPath());

        directories(treeNodes,file);

        JTree tree = new JTree(treeNodes);
        JScrollPane scrollPane = new JScrollPane(tree);

        tree.setBackground(Color.DARK_GRAY);
        scrollPane.setBackground(Color.DARK_GRAY);

        this.add(scrollPane);
        this.setVisible(true);

    }

    public void directories(DefaultMutableTreeNode dir, File file){
        File[] files = file.listFiles();
        if(files != null){
            for(File f : files){
                DefaultMutableTreeNode subDir = new DefaultMutableTreeNode(f.getName());
                dir.add(subDir);
                if(f.isDirectory()){
                    directories(subDir,f);
                }
            }
        }
    }
}


