package mainPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")
public class Main extends JFrame {
    // 带有应用程序窗口原始尺寸的常量
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult; //
    // 表格单元格可视化器
    private FunctionTableCellRenderer renderer = new FunctionTableCellRenderer();
    private mainPackage.FunctionTableModel data;  // 带有计算结果的数据模型

    public Main() {
        super("函数表格"); // 强制性的祖先构造器调用
        setSize(WIDTH, HEIGHT);// 设置窗口尺寸
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2, // 将应用程序窗口置于屏幕中央
                (kit.getScreenSize().height - HEIGHT)/2);

//创建菜单栏
        MenuBar menuBar = new MenuBar();
        //设置菜单栏
        setMenuBar(menuBar);
        //创建菜单
        Font font = new Font("Arial", Font.PLAIN, 12);

        Menu m1 = new Menu("文件", true);
        m1.setFont(font);
        //向菜单栏中添加菜单
        menuBar.add(m1);
        //创建选项
        MenuItem menuItem1 = new MenuItem("新建");
        menuItem1.setFont(font);setFont(font);
        MenuItem menuItem2 = new MenuItem("打开");
        menuItem2.setFont(font);
        //向菜单中添加选项
        m1.add(menuItem1);
        m1.add(menuItem2);
        Menu m2 = new Menu("编辑", true);
        m2.setFont(font);
        menuBar.add(m2);

        Menu m3 = new Menu("帮助", true);
        m3.setFont(font);
        menuBar.setHelpMenu(m3);
        MenuItem menuItem5 = new MenuItem("Справка");
        menuItem5.addActionListener(e -> {
            String message = "Автор: Сунь\nГруппа: 8";

            JOptionPane.showMessageDialog(null, message, "О программе", JOptionPane.INFORMATION_MESSAGE);
        });
        menuItem5.setFont(font);
        m3.add(menuItem5);


        textFieldFrom = new JTextField("0.0", 10);
        // 设置一个等于首选尺寸的最大尺寸，以便
        // 防止输入框的尺寸增加
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());

        // 创建一个字段来输入X步进值
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        // 创建一个 "水平堆叠的盒子 "类型的容器
        Box hboxXRange = Box.createHorizontalBox();

        hboxXRange.add(Box.createHorizontalGlue());
        hboxXRange.add(new JLabel("X 最初的:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldFrom);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("X 最终:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldTo);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("步骤为 X:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldStep);
        hboxXRange.add(Box.createHorizontalGlue());

        Box Formula = Box.createHorizontalBox();
        Formula.add(Box.createHorizontalGlue());
        JLabel F = new JLabel("公式:  Y = X - P ");
        F.setFont(font);
        Formula.add(F);
        Formula.add(Box.createHorizontalGlue());

        JTextField textFieldParam = new JTextField("0.0", 10);
        textFieldParam.setMaximumSize(textFieldStep.getPreferredSize());

        Action P3 = new AbstractAction("p3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldParam.setText(Double.toString(3));
            }
        };

        Box Param = Box.createHorizontalBox();
        Param.add(Box.createHorizontalGlue());
        Param.add(new JLabel("P: "));
        Param.add(Box.createHorizontalStrut(10));
        Param.add(textFieldParam);
        Param.add(Box.createHorizontalGlue());

        Box Nast = Box.createVerticalBox();
        Nast.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "设置:"));
        Nast.add(Box.createVerticalGlue());
        Nast.add(hboxXRange);
        Nast.add(Box.createHorizontalStrut(20));
        Nast.add(Formula);
        Nast.add(Box.createHorizontalStrut(20));
        Nast.add(Param);
        Nast.add(Box.createVerticalGlue());

        // 设置较大的区域首选尺寸
        // 最小，所以在铺设时，该区域完全不受挤压。
        Nast.setPreferredSize(new Dimension(
                new Double(Nast.getMaximumSize().getWidth()).intValue(),
                new Double(Nast.getMinimumSize().getHeight()*1.5).intValue()));
        // 将该区域设置为布局的顶部（北部）。
        getContentPane().add(Nast, BorderLayout.NORTH);

        // 创建一个 "计算 "按钮
        JButton buttonCalc = new JButton("计算");
        buttonCalc.setFont(font);
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    // 读取一个段的开始和结束值，一个步骤
                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());
                    Double param = Double.parseDouble(textFieldParam.getText());

                    // 从读取的数据中，创建一个表模型的实例
                    data = new mainPackage.FunctionTableModel(from, to, step, param);
                    // 创建一个新的表实例
                    JTable table = new JTable(data);
                    //设置为Double类的单元格显示器。
                    // 开发了可视化工具
                    table.setDefaultRenderer(Double.class, renderer);
                    // 设置表格行的大小为30像素
                    table.setRowHeight(30);
                    //从hBoxResult容器中删除所有嵌套元素。
                    hBoxResult.removeAll();
                    // 在hBoxResult中加入被 "包裹 "在面板中的表格。
                    //有滚动条
                    hBoxResult.add(new JScrollPane(table));
                    //重新排列hBoxResult中的组件并执行
                    //重绘
                    hBoxResult.revalidate();

                } catch (NumberFormatException ex) {
                    // 在发生数字转换错误的情况下，显示一个关于
                    //错误
                    JOptionPane.showMessageDialog(Main.this,
                            "数字格式不正确",
                            "数字格式不正确", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 创建一个 "清除字段 "按钮
        JButton buttonReset = new JButton("清除");
        buttonReset.setFont(font);
        // 将动作设置为 "清除字段"，并链接到按钮。
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // 将输入字段设置为默认值
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");
                // 删除hBoxResult容器的所有嵌套元素。
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                getContentPane().validate();
//                // 重新绘制hBoxResult本身

            }
        });

        // 将创建的按钮放在一个容器中
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createEtchedBorder());
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        // 设置首选区域大小，大于最小值，以
        // 窗户的布局根本没有挤压到这个区域
        hboxButtons.setPreferredSize(new Dimension(
                new Double(hboxButtons.getMaximumSize().getWidth()).intValue(),
                new Double(hboxButtons.getMinimumSize().getHeight()*1.5).intValue()));
        // 将按钮容器放置在边界的下部（南部）区域
//布局
        getContentPane().add(hboxButtons, BorderLayout.SOUTH);

        // 输出结果的区域仍然是空的
        hBoxResult = Box.createHorizontalBox();
        // 在主（中央）区域设置容器hBoxResult
//边界布局
        getContentPane().add(hBoxResult);
    }

    public static void main(String[] args) {
        // Создать экземпляр главного окна
        Main frame = new Main();
        // Задать действие, выполняемое при закрытии окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Показать главное окно приложения
        frame.setVisible(true);
    }
}
