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
        JFrame frame = new JFrame();
        JaguarRefresh refresh = new JaguarRefresh();

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Jaguar");
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
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
        jToolBar.add(refreshButton);
        jToolBar.add(deleteButton);
        Container pane = frame.getContentPane();
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
                        refresh.refreshTree(new File("/home/groundzero/Documents"),frame);
                    } else {
                        JOptionPane.showMessageDialog(null, "Falaha na exclusão do arquivo, verifique a permissão ou se o arquivo/diretório ainda existe.");
                    }
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh.refreshTree(new File("/home/groundzero/Documents"),frame);
            }
        });
        //Fim func. Bot.

        //"Seta" a visibilidade da janela e adiciona um sistema de rolgaem caso a pagina seja muito grande
        frame.add(scrollPane);
        frame.setVisible(true);
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

    public static void directories(DefaultMutableTreeNode dir, File file){
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
