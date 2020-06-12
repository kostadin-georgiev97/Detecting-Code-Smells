package Couplers.MessageChainsBetter;

class Intermediate {
    private Child child;

    public Child getChild() {
        return child;
    }

    public void intermediateOp(){
        System.out.println("An intermediate operation");
    };
}