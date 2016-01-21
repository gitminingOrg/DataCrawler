package metrics22;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ClassVisitor extends ASTVisitor {
	String content;
	List<Integer> lineEnd = new ArrayList<Integer>();
	public ClassVisitor(String content, List<Integer> lineEnd) {
		super();
		this.content = content;
		this.lineEnd = lineEnd;
	}

	String className;
	List<String> classFieldNames = new ArrayList<String>();
	List<String> methodsNames = new ArrayList<String>();
	List<Assign> assigns = new ArrayList<Assign>();
	List<VarDeclare> varDeclares = new ArrayList<VarDeclare>();
	List<SpecialStmt> specialStmts = new ArrayList<SpecialStmt>();
	List<String> methodsParameterNames = new ArrayList<String>();
	List<metrics22.Expression> express=new ArrayList<metrics22.Expression>();
	
//////////////////////////////////////////// level class /////////////////////////////////////////////////
	@Override
	/**
	 * class name fetch
	 */
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		node.getStartPosition();
		node.getLength();
		className = node.getName().getIdentifier();
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		// TODO Auto-generated method stub
		for (Object obj: node.fragments()) {  
            VariableDeclarationFragment v = (VariableDeclarationFragment)obj;  
            classFieldNames.add(v.getName().getIdentifier());
            VarDeclare varDeclare = new VarDeclare(v.getName().getIdentifier(), node.getStartPosition(), node.getLength(), node.toString());
            varDeclares.add(varDeclare);
		}  
        Assign assign = new Assign(node.getStartPosition(), node.getLength(), node.toString());
        assigns.add(assign);
		return super.visit(node);
	}
////////////////////////////////////////////level class /////////////////////////////////////////////////
	


////////////////////////////////////////////level method /////////////////////////////////////////////////
	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		node.getStartPosition();
		List<SingleVariableDeclaration> parameters = node.parameters();
		for (SingleVariableDeclaration singleVariableDeclaration : parameters) {
			String name = singleVariableDeclaration.getName().toString();
			methodsParameterNames.add(name);
		}
		Block block = node.getBody();
		return super.visit(node);
	}
////////////////////////////////////////////level method /////////////////////////////////////////////////

////////////////////////////////////////////level statement /////////////////////////////////////////////////
	@Override
	public boolean visit(ExpressionStatement node) {
		// TODO Auto-generated method stub
		metrics22.Expression e=new metrics22.Expression(node.getStartPosition(), node.getLength(), node.toString());
		express.add(e);
		return super.visit(node);
	}
	/**
	 * Assignment类（赋值表达式）
	 */
	@Override
	public void endVisit(Assignment node) {
		// TODO Auto-generated method stub
		Expression expressionLeft = node.getLeftHandSide();
		Expression expressionRight = node.getRightHandSide();
		Assign assign = new Assign(node.getStartPosition(), node.getLength(), node.toString());
        assigns.add(assign);
		super.endVisit(node);
	}

	@Override
	public void endVisit(MethodInvocation node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// TODO Auto-generated method stub
		System.out.println("aaaaa");
		List<VariableDeclarationFragment >frags = node.fragments();
		for (VariableDeclarationFragment frg : frags) {
            VarDeclare varDeclare = new VarDeclare(frg.getName().getIdentifier(), node.getStartPosition(), node.getLength(), node.toString());
            varDeclares.add(varDeclare);
		}
        Assign assign = new Assign(node.getStartPosition(), node.getLength(), node.toString());
        assigns.add(assign);
		return super.visit(node);
	}
	
	
	
@Override
	public void endVisit(CatchClause node) {
		// TODO Auto-generated method stub
		SpecialStmt specialStmt = new SpecialStmt(node.toString(), node.getStartPosition(), node.getLength(), "catch");
		specialStmts.add(specialStmt);
		super.endVisit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		// TODO Auto-generated method stub
		SpecialStmt specialStmt = new SpecialStmt(node.toString(), node.getStartPosition(), node.getLength(), "for");
		specialStmts.add(specialStmt);
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		// TODO Auto-generated method stub
		SpecialStmt specialStmt = new SpecialStmt(node.toString(), node.getStartPosition(), node.getLength(), "if");
		specialStmts.add(specialStmt);
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		// TODO Auto-generated method stub
		SpecialStmt specialStmt = new SpecialStmt(node.toString(), node.getStartPosition(), node.getLength(), "while");
		specialStmts.add(specialStmt);
		return super.visit(node);
	}

	////////////////////////////////////////////level statement /////////////////////////////////////////////////
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}



	@Override
	public boolean visit(Block node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MemberRef node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MemberValuePair node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(Modifier node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}


	@Override
	public boolean visit(TypeParameter node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRef node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

}
