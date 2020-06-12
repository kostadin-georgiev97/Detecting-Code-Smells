package Couplers.MessageChainsBetter;
// From https://stackoverflow.com/questions/2909321/how-do-i-correct-feature-envy-in-this-case
// Message Chain operations on child are being accessed via the intermediateContainer field in Parent

class Client {
    private Parent parent;

    public void something() {
        parent.getIntermediate().getChild().something();
    }

    public void somethingElse() {
        parent.getIntermediate().getChild().somethingElse();
    }

    public void intermediate() {
        parent.getIntermediate().intermediateOp();
    }
}
