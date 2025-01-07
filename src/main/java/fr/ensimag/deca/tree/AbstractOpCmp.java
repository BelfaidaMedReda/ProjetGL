package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;

/**
 *
 * @author gl50
 * @date 01/01/2025
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if(!type1.isBinaryOpSupported(getOperatorName(),type2)){
            throw new ContextualError("Comparaison Operation " +getOperatorName()+
                    " not supported or Types " + type1.getName() + " "+ type2.getName() +" not compatible with the current operation",getLocation());
        }
        if(!type1.sameType(type2)){
            if(type1.isInt()){
                ConvFloat cvf = new ConvFloat(getLeftOperand());
                setLeftOperand(cvf);
                cvf.setType(type2);
            }
            else {
                ConvFloat cvf = new ConvFloat(getRightOperand());
                setRightOperand(cvf);
                cvf.setType(type1);
            }
        }
        Type booleanType = new BooleanType(compiler.createSymbol("boolean"));
        setType(booleanType);
        return booleanType;
    }


}
