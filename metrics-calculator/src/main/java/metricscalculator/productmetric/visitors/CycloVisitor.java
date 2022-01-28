package metricscalculator.productmetric.visitors;

import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CycloVisitor extends VoidVisitorAdapter<Void> {

    private int count = 1;

    @Override
    public void visit(IfStmt n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    @Override
    public void visit(ConditionalExpr n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    @Override
    public void visit(SwitchEntry n, Void arg) {
        super.visit(n, arg);
        if (!n.toString().contains("default"))
            count++;
    }

    @Override
    public void visit(CatchClause n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    @Override
    public void visit(ForEachStmt n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        super.visit(n, arg);
        count++;
    }

    public int getCount() {
        return count;
    }

}
