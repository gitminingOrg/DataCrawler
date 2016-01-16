package metrics22;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
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
//////////////////////////////////////////// level class /////////////////////////////////////////////////
	@Override
	/**
	 * class name fetch
	 */
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("Class:\t"+node.getName());
		className = node.getName().getIdentifier();
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		// TODO Auto-generated method stub
		for (Object obj: node.fragments()) {  
            VariableDeclarationFragment v = (VariableDeclarationFragment)obj;  
            classFieldNames.add(v.getName().getIdentifier());
            System.out.println("Field:\t" + v.getName());
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
		System.out.println("Method:\t"+node.getName());
		List<SingleVariableDeclaration> parameters = node.parameters();
		for (SingleVariableDeclaration para : parameters) {
			System.out.println(para.getName());
		}
		Block block = node.getBody();
		return super.visit(node);
	}
////////////////////////////////////////////level method /////////////////////////////////////////////////

////////////////////////////////////////////level statement /////////////////////////////////////////////////
	@Override
	public boolean visit(ExpressionStatement node) {
		// TODO Auto-generated method stub
		System.out.print("ExpressionStatement\t"+node.getStartPosition());
		System.out.println(node.getExpression());
		return super.visit(node);
	}
	/**
	 * Assignment类（赋值表达式）
	 */
	@Override
	public void endVisit(Assignment node) {
		// TODO Auto-generated method stub
		System.out.println("assignment!!!!!!!!!!!!!!!!!!!"+node.toString()+"     start"+node.getStartPosition()+"    length"+node.getLength());
		Expression expressionLeft = node.getLeftHandSide();
		System.out.println((SimpleName)expressionLeft);
		Expression expressionRight = node.getRightHandSide();
		Assign assign = new Assign(node.getStartPosition(), node.getLength(), node.toString());
        assigns.add(assign);
		super.endVisit(node);
	}

	@Override
	public void endVisit(MethodInvocation node) {
		// TODO Auto-generated method stub
		System.out.println("MethodInvocation!!!!!!!!!!!!!!!!!!!"+node.toString()+"     "+node.getStartPosition()+" arg_sze"+node.arguments().size());
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// TODO Auto-generated method stub
		List<VariableDeclarationFragment >frags = node.fragments();
		for (VariableDeclarationFragment frg : frags) {
			System.out.println("variable: " + frg.getName() + "     "+node.toString());
		}
        Assign assign = new Assign(node.getStartPosition(), node.getLength(), node.toString());
        assigns.add(assign);
		return super.visit(node);
	}
	
////////////////////////////////////////////level statement /////////////////////////////////////////////////
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		// TODO Auto-generated method stub
		System.out.println("typeDeclare: " + node.getDeclaration().getName());
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
		System.out.println(node.getName());
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
		System.out.println(node.getKeyword());
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		// TODO Auto-generated method stub
		System.err.println(node.toString()+"/blockComment");
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		// TODO Auto-generated method stub
		System.err.println(node.toString()+"/lineComment");
		return super.visit(node);
	}


	@Override
	public boolean visit(TypeParameter node) {
		// TODO Auto-generated method stub
		System.out.println("ClassPara:\t"+node.getName());
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRef node) {
		// TODO Auto-generated method stub
		System.out.println("MethodRef:\t"+node.getName());
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		// TODO Auto-generated method stub
		System.out.println("MethodRefParameter:\t"+node.getName());
		return super.visit(node);
	}

}
