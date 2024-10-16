package Lince;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JaguarUI extends JFrame implements ActionListener {
    private JTable table;
    private JTree tree;
    private DefaultMutableTreeNode rootNode;

    public JaguarUI() {
        setTitle("Jaguar/Explorer");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());


        File rootDirectory = new File(System.getProperty("user.home") + "/Documents");
        rootNode = new DefaultMutableTreeNode(rootDirectory.getPath());
        tree = new JTree(rootNode);

        directories(rootNode, rootDirectory);
        table = createFileTable(rootDirectory);

        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setBackground(Color.DARK_GRAY);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.DARK_GRAY);

        // Panel principal com SplitPane para dividir Árvore e Tabels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, tableScrollPane);
        splitPane.setDividerLocation(250);

        // Toolbar com os botões
        JToolBar toolBar = createToolBar(rootDirectory);

        add(toolBar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    // Inicializa a JTable
    private JTable createFileTable(File directory) {
        String[] columnNames = {"Nome", "Tamanho", "Última Modificação"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                Object[] rowData = {file.getName(), file.length(), new Date(file.lastModified())};
                model.addRow(rowData);
            }
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        return table;
    }

    // Cria uma barra de ferramentas com botões
    private JToolBar createToolBar(File rootDirectory) {
        JToolBar toolBar = new JToolBar();
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");

        refreshButton.addActionListener(e -> refreshTree(rootDirectory));
        deleteButton.addActionListener(e -> deleteSelectedFile());
        searchButton.addActionListener(e -> searchFile());

        toolBar.add(refreshButton);
        toolBar.add(deleteButton);
        toolBar.add(searchButton);

        return toolBar;
    }

    // Atualiza a árvore e a tabela
    private void refreshTree(File directory) {
        rootNode.removeAllChildren();
        directories(rootNode, directory);
        tree.updateUI();

        // Atualiza a tabela
        table.setModel(createFileTable(directory).getModel());
    }

    // Deleta o arquivo ou diretório selecionado na árvore
    private void deleteSelectedFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) return;

        File fileToDelete = new File(getFullPath(selectedNode));
        int confirm = JOptionPane.showConfirmDialog(this, "Quer deletar o arquivo selecionado?");
        if (confirm == JOptionPane.YES_OPTION && fileToDelete.delete()) {
            JOptionPane.showMessageDialog(this, "Arquivo deletado com sucesso!");
            refreshTree(fileToDelete.getParentFile());
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao deletar o arquivo.");
        }
    }

    // Abre um JFileChooser para buscar arquivos
    private void searchFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "webp"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Desktop.getDesktop().open(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void directories(DefaultMutableTreeNode parent, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getName());
                parent.add(childNode);
                if (file.isDirectory()) {
                    directories(childNode, file);
                }
            }
        }
    }

    // Retorna o caminho completo do node selecionado
    public String getFullPath(DefaultMutableTreeNode node) {
        StringBuilder fullPath = new StringBuilder();
        TreeNode[] nodes = node.getPath();
        for (int i = 0; i < nodes.length; i++) {
            fullPath.append(nodes[i].toString());
            if (i < nodes.length - 1) {
                fullPath.append(File.separator);
            }
        }
        return fullPath.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JaguarUI::new);
    }
}
