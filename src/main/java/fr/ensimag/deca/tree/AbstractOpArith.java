package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl50
 * @date 01/01/2025
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if(!type1.isBinaryOpSupported(getOperatorName(),type2)){
           throw new ContextualError("Arithmetic binary Operation " +getOperatorName()+
                   " not supported or Types " + type1.getName() + " "+ type2.getName() +" not compatible with the current operation",getLocation());
        }
        if(type1.sameType(type2)){
            setType(type1);
            return type1;
        }
        else{
            if(type1.isInt()){
                ConvFloat cvf = new ConvFloat(getLeftOperand());
                setLeftOperand(cvf);
                cvf.setType(type2);
                return type2;
            }
            else{
                ConvFloat cvf = new ConvFloat(getRightOperand());
                setRightOperand(cvf);
                cvf.setType(type1);
                return type1;
            }
        }
    }
}
