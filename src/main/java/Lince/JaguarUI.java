package Lince;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JaguarUI extends JFrame {
    public JaguarUI() {

        //Acessa o diretório principal (Home)
        String userHome = System.getProperty("user.home");
        File file = new File("/home/groundzero/Documents");

        //Verifica se o Caminho existe
        if(!file.exists()){
            System.out.println("Directory not found" + file.getPath());
            return;
        }else{
            System.out.println("Directory accessed: " + file.getPath());
        }
        //Fim Ver.

        //Verifica se há algum arquivo ou pasta no caminho
        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Nothing found in: " + userHome);
        }else{
            System.out.println("Files found in userhome: " + userHome);
            for(File f : files){
                System.out.println(f.getName());
            }
        }
        //Fim Ver. de Arq.

        //Ajustes da janela do Programa
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Jaguar");
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        //Fim Aj. Jan.

        //Inicialização da ToolBar
        JToolBar jToolBar = new JToolBar();

        // Botões
        JButton nextButton = new JButton("Next");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh");
        //Fim Botões

        //Conf. da Toolbar
        jToolBar.add(nextButton);
        jToolBar.add(deleteButton);
        Container pane = this.getContentPane();
        pane.add(jToolBar, BorderLayout.NORTH);
        //Fim Conf. Toolbar

        //Root Node da JTree
        DefaultMutableTreeNode treeNodes = new DefaultMutableTreeNode(file.getPath());
        directories(treeNodes,file);
        JTree tree = new JTree(treeNodes);
        //Fim Node Tree

        //Personalização da Janela
        JScrollPane scrollPane = new JScrollPane(tree);
        tree.setBackground(Color.DARK_GRAY);
        scrollPane.setBackground(Color.DARK_GRAY);
        //Fim pers. Jan.

        //Funçoes do(s) botão(ões)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }
                File fileDelete = new File(getFullPath(selectedNode));

                if (selectedNode == null) {
                    System.out.println("Nenhum arquivo ou diretório encontrado");
                }

                int confExclusao = JOptionPane.showConfirmDialog(null, "Quer deletar o arquivo selecionado?");
                if (confExclusao == JOptionPane.YES_OPTION) {
                    if (fileDelete.delete()) {
                        JOptionPane.showMessageDialog(null, "Arquivo Deletado!");
                        refreshTree(new File("/home/groundzero/Documents"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Falaha na exclusão do arquivo, verifique a permissão ou se o arquivo/diretório ainda existe.");
                    }
                }
            }
        });
        //Fim func. Bot.

        //"Seta" a visibilidade da janela e adiciona um sistema de rolgaem caso a pagina seja muito grande
        this.add(scrollPane);
        this.setVisible(true);
        }
        //Fim Scroll

    //Método para obter o caminho do objeto selecionado
    public String getFullPath(DefaultMutableTreeNode node){
        StringBuilder fullPath = new StringBuilder();
        TreeNode[] nodes = node.getPath();
        for(int i = 0; i < nodes.length; i++){
            fullPath.append(nodes[i].toString());
            if(i < nodes.length - 1){
                fullPath.append(File.separator);
            }
        }
        return fullPath.toString();
    }
    //Fim Mét. p/ Obj.

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

    public void refreshTree(File root){
        DefaultMutableTreeNode newTree = new DefaultMutableTreeNode(root.getPath());
        directories(newTree,root);

        this.getContentPane().removeAll();

        JTree tree = new JTree(newTree);
        JScrollPane scrollPane = new JScrollPane(tree);
        tree.setBackground(Color.DARK_GRAY);
        scrollPane.setBackground(Color.DARK_GRAY);

        JButton nextButton = new JButton("Next");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh");

        JToolBar jToolBar = new JToolBar();
        jToolBar.add(nextButton);
        jToolBar.add(deleteButton);

        Container pane = this.getContentPane();
        pane.add(jToolBar, BorderLayout.NORTH);

        this.getContentPane().add(scrollPane);

        this.revalidate();
        this.repaint();
    }
}


