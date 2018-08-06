package java_bootcamp;

import co.paralleluniverse.fibers.Suspendable;

import net.corda.core.flows.*;
import net.corda.core.identity.Party;

@InitiatingFlow
@StartableByRPC
public class TwoPartyFlow extends FlowLogic<Integer>
{
    private Party counterParty;

    public TwoPartyFlow(Party counterParty)
    {
        this.counterParty =counterParty;
    }


    @Suspendable
    public Integer call() throws FlowException {
        FlowSession session = initiateFlow(counterParty);
        session.send(1);
        return null;
    }
}
