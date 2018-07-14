package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Stack;

/**
 * This class represents SmartScriptEngine. It is used for executing .smscr files.
 */
public class SmartScriptEngine {

    /**
     * Root node of .smscr tree.
     */
    private DocumentNode documentNode;

    /**
     * Context used for logic of engine.
     */
    private RequestContext requestContext;

    /**
     * Multistack used for storing variables.
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * Visitor used as a engine that executes script.
     */
    private INodeVisitor visitor = new INodeVisitor() {

        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String variable = node.getVariable().asText();
            ValueWrapper endExpression = new ValueWrapper(node.getEndExpression().asText());
            ValueWrapper stepExpression = new ValueWrapper(node.getStepExpression().asText());

            multistack.push(variable, new ValueWrapper(node.getStartExpression().asText()));

            while (multistack.peek(variable).numCompare(endExpression) <= 0) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(this);
                }
                multistack.peek(variable).add(stepExpression);
            }
            multistack.pop(variable);
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<ValueWrapper> echoStack = new Stack<>();
            for (Element el : node.getElements()) {
                if (el instanceof ElementConstantDouble) {
                    echoStack.push(new ValueWrapper(((ElementConstantDouble) el).getValue()));
                } else if (el instanceof ElementConstantInteger) {
                    echoStack.push(new ValueWrapper(((ElementConstantInteger) el).getValue()));
                } else if (el instanceof ElementString) {
                    echoStack.push(new ValueWrapper(el.asText()));
                } else if (el instanceof ElementVariable) {
                    echoStack.push(new ValueWrapper(multistack.peek(el.asText()).getValue()));
                } else if (el instanceof ElementOperator) {
                    ValueWrapper firstOperand = echoStack.pop();
                    ValueWrapper secondOperand = echoStack.pop();
                    switch (el.asText()) {
                        case "+":
                            firstOperand.add(secondOperand);
                            echoStack.push(firstOperand);
                            break;
                        case "-":
                            firstOperand.sub(secondOperand);
                            echoStack.push(firstOperand);
                            break;
                        case "*":
                            firstOperand.multiply(secondOperand);
                            echoStack.push(firstOperand);
                            break;
                        case "/":
                            firstOperand.divide(secondOperand);
                            echoStack.push(firstOperand);
                            break;
                        default:
                            throw new RuntimeException("Invalid operator!");

                    }
                } else if (el instanceof ElementFunction) {
                    switch (el.asText()) {
                        case "@sin":
                            ValueWrapper sinElement = echoStack.pop();
                            ValueWrapper sinNewElement = new ValueWrapper(Math.sin(sinElement.doubleValue() * Math
                                    .PI / 180));
                            echoStack.push(sinNewElement);
                            break;
                        case "@decfmt":
                            DecimalFormat format = new DecimalFormat(echoStack.pop().toString());
                            double element = echoStack.pop().doubleValue();
                            echoStack.push(new ValueWrapper(format.format(element)));
                            break;
                        case "@dup":
                            ValueWrapper dupElement = echoStack.pop();
                            echoStack.push(dupElement);
                            echoStack.push(dupElement);
                            break;
                        case "@swap":
                            ValueWrapper swapFirst = echoStack.pop();
                            ValueWrapper swapSecond = echoStack.pop();
                            echoStack.push(swapFirst);
                            echoStack.push(swapSecond);
                            break;
                        case "@setMimeType":
                            ValueWrapper mimeWrapper = echoStack.pop();
                            requestContext.setMimeType(mimeWrapper.toString());
                            break;
                        case "@paramGet":
                            ValueWrapper paramGetDv = echoStack.pop();
                            ValueWrapper paramGetName = echoStack.pop();
                            String paramGetValue = requestContext.getParameter(paramGetName.toString());
                            if (paramGetValue == null) {
                                paramGetValue = paramGetDv.toString();
                            }
                            echoStack.push(new ValueWrapper(paramGetValue));
                            break;
                        case "@pparamGet":
                            ValueWrapper pparamGetDv = echoStack.pop();
                            ValueWrapper pparamGetName = echoStack.pop();
                            String pparamGetValue = requestContext.getPersistentParameter(pparamGetName.toString());
                            if (pparamGetValue == null) {
                                pparamGetValue = pparamGetDv.toString();
                            }
                            echoStack.push(new ValueWrapper(pparamGetValue));
                            break;
                        case "@pparamSet":
                            ValueWrapper pparamSetName = echoStack.pop();
                            ValueWrapper pparamSetValue = echoStack.pop();
                            requestContext.setPersistentParameters(pparamSetName.toString(), pparamSetValue.toString());
                            break;
                        case "@pparamDel":
                            ValueWrapper pparamDelName = echoStack.pop();
                            requestContext.removePersistentParameters(pparamDelName.toString());
                            break;
                        case "@tparamGet":
                            ValueWrapper tparamGetDv = echoStack.pop();
                            ValueWrapper tparamGetName = echoStack.pop();
                            String tparamGetValue = requestContext.getTemporaryParameter(tparamGetName.toString());
                            if (tparamGetValue == null) {
                                tparamGetValue = tparamGetDv.toString();
                            }
                            echoStack.push(new ValueWrapper(tparamGetValue));
                            break;
                        case "@tparamSet":
                            ValueWrapper tparamSetName = echoStack.pop();
                            ValueWrapper tparamSetValue = echoStack.pop();
                            requestContext.setTemporaryParameter(tparamSetName.toString(), tparamSetValue.toString());
                            break;
                        case "@tparamDel":
                            ValueWrapper tparamDelName = echoStack.pop();
                            requestContext.removeTemporaryParameter(tparamDelName.toString());
                            break;
                        default:
                            throw new RuntimeException("Invalid function!");

                    }

                }


            }


            new ArrayDeque<>(echoStack).iterator().forEachRemaining(t -> {
                try {
                    requestContext.write(String.valueOf(t.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    };

    /**
     * Basic constructor.
     *
     * @param documentNode   DocumentNode
     * @param requestContext Context
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext
            requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * This method is used for executing file loaded into engine.
     */
    public void execute() {
        documentNode.accept(visitor);
    }


}
