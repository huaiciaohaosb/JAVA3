package mainPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class FunctionTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public FunctionTableCellRenderer() {
        // 只显示5位小数
        formatter.setMaximumFractionDigits(5);
        // 不要使用分组（不要用逗号或空格分隔千位数）。
        formatter.setGroupingUsed(false);
        // 将小数部分设置为用点而不是逗号分隔。
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        // 将铭文放在面板内
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 左边
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean
            isSelected, boolean hasFocus, int row, int col) {
        // 用格式化的方式将双数转换为字符串
        String formattedDouble = formatter.format(value);
        // 将题词文本设置为等于数字的字符串表示。
        label.setText(formattedDouble);
        if (col==1 && needle!=null && needle.equals(formattedDouble)) {
            // 列号=1（即第二列）+针不为空（意味着在寻找什么）。
            //针值与表格中的单元格值相同--给背景上色
            // 红色的面板
            panel.setBackground(Color.RED);
        } else {
            // 否则就变成纯白色
            panel.setBackground(Color.WHITE);
        }
        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}