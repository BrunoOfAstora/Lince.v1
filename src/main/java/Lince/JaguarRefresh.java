package Lince;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static Lince.JaguarUI.directories;

public class JaguarRefresh extends JFrame {

        public void refreshTree(File root, JFrame frame){

            DefaultMutableTreeNode newTree = new DefaultMutableTreeNode(root.getPath());
            directories(newTree,root);
            File file = new File("/home/groundzero/Documents");

            frame.getContentPane().removeAll();

            JTree tree = new JTree(newTree);
            JScrollPane scrollPane = new JScrollPane(tree);
            tree.setBackground(Color.DARK_GRAY);
            scrollPane.setBackground(Color.DARK_GRAY);

            JButton nextButton = new JButton("Next");
            JButton deleteButton = new JButton("Delete");
            JButton backButton = new JButton("Back");
            JButton refreshButton = new JButton("Refresh");

            JToolBar jToolBar = new JToolBar();
            jToolBar.add(refreshButton);
            jToolBar.add(deleteButton);

            Container pane = frame.getContentPane();
            pane.add(jToolBar, BorderLayout.NORTH);

            frame.getContentPane().add(scrollPane);

            DefaultMutableTreeNode treeNodes = new DefaultMutableTreeNode(file.getPath());
            directories(treeNodes,file);



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
                            refreshTree(new File("/home/groundzero/Documents"),frame);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falaha na exclusão do arquivo, verifique a permissão ou se o arquivo/diretório ainda existe.");
                        }
                    }
                }
            });


            frame.revalidate();
            frame.repaint();
        }

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
}

