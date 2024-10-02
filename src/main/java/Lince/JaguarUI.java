package Lince;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class JaguarUI extends JFrame {

    public JaguarUI() {

        File file = new File("/home/");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Jaguar");
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        DefaultMutableTreeNode treeNodes = new DefaultMutableTreeNode(file.getPath());

        directories(treeNodes,file);

        JTree tree = new JTree(treeNodes);
        tree.setBounds(25, 25, 200, 100);

        JScrollPane scrollPane = new JScrollPane(tree);
        this.add(scrollPane);

        this.add(tree);

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

