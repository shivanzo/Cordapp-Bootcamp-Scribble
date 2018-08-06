package java_bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import java.security.PublicKey;
import java.util.List;

public class HouseContract implements Contract
{

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException
    {
        if(tx.getCommands().size() != 1)
            throw new IllegalArgumentException("Transaction must have one command");

        Command command  = tx.getCommand(0);
        List<PublicKey> requiredSigners = command.getSigners();
        CommandData commandType = command.getValue();

        if(commandType instanceof Register)
        {
            // Shape contraints
                if(tx.getInputStates().size() !=0)
                    throw new IllegalArgumentException("Registration transaction must have no inputs.");
                if(tx.getOutputStates().size() != 1)
                    throw new IllegalArgumentException("Registration transaction must have one output.");

                //content constraints.
                ContractState outputState = tx.getOutput(0);
                if(!(outputState instanceof HouseState))
                    throw new IllegalArgumentException("Output must be a house state.");

                HouseState housestate = (HouseState) outputState;
                if(housestate.getAddress().length() <= 3)
                    throw new IllegalArgumentException("Address must be longer than  3 characters.");
                if(housestate.getOwner().equals("Brazil"))
                    throw new IllegalArgumentException("Not allowed to register for Brazilan owners.");

                //Required signer constraints
                Party owner = housestate.getOwner();
                PublicKey ownerKey = owner.getOwningKey();

                if(!(requiredSigners.contains(ownerKey)))
                    throw new IllegalArgumentException("Owner of the house must sign registration.");

            }


            else if(commandType instanceof Transfer) {

            //shape constraints.
            if(tx.getInputStates().size() != 1)
                throw new IllegalArgumentException("Must have atleast one input.");
            if(tx.getOutputStates().size() != 1)
                throw new IllegalArgumentException("Must have atleast one output");

            //Content constraints
            ContractState input = tx.getInput(0);
            ContractState output = tx.getOutput(0);

            if(!(input instanceof HouseState))
                throw new IllegalArgumentException("Input not a instance of HouseState");
            if(!(output instanceof HouseState))
                throw new IllegalArgumentException("Output not a instance of HouseState");

            HouseState inputHouse =(HouseState) input;
            HouseState outputHouse = (HouseState) output;


            if(!(inputHouse.getAddress().equals(outputHouse.getAddress())))
                throw new IllegalArgumentException("In a transfer, address can't change");

            if(inputHouse.getOwner().equals(outputHouse.getOwner()))
                throw new IllegalArgumentException("In a transfer, Owner must change");

            //Signer Constraints
            Party inputOwner =  inputHouse.getOwner();
            Party outputOwner = outputHouse.getOwner();



            if(!(requiredSigners.contains(inputOwner.getOwningKey())))
                throw new IllegalArgumentException("Owner sign is needed");

            if(!(requiredSigners.contains(outputOwner.getOwningKey())))
                throw new IllegalArgumentException("next owner sign is also needed");

        }
        else
            {
                throw new IllegalArgumentException("Command type not recognized.");
            }

        }

        public static class Register implements CommandData{}
        public static class Transfer implements CommandData{}


}
