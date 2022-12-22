package mainPackage;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import java.util.Comparator;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
@SuppressWarnings("serial")
public class FunctionTableModel extends AbstractTableModel {
    private Double from, to, step, param;       // 从、到和步

    public FunctionTableModel(Double from, Double to, Double step, Double param) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.param = param;
    }

    public Double getFrom() {
        return from;                //由于我们有所有这些变量都是私有的，所以返回
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public Double getParam() {
        return param;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        //根据步骤计算参数值的数量
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        //计算X的值（col=0）为START_TIME + STEP*NOMER_STRONG
        double x = from + step * row;
        double y = x - param;

        if (col == 0) {
            return x;
        } else if (col == 1) {
            return y;
        } else {
            return x < 0;
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "价值 х";
            case 1:
                return "价值 у";
            case 2:
                return "x < 0";
        }
        return "";
    }

    public Class<?> getColumnClass(int col) {
        //它的第1列和第2列包含Double类型的值
        if (col != 2)
            return Double.class;
        else {
            return Boolean.class;
        }
    }
}
