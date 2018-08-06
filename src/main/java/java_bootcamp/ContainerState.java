package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContainerState implements ContractState
{
    private int width;
    private int depth;
    private int height;
    private String contents;
    private Party owner;
    private Party carrier;

    public ContainerState(int width, int depth, int height, String contents, Party owner, Party carrier)
    {
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.contents = contents;
        this.owner = owner;
        this.carrier = carrier;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public String getContents() {
        return contents;
    }

    public Party getOwner() {
        return owner;
    }

    public Party getCarrier() {
        return carrier;
    }


    public static void main(String[] args) {
        Party jetpackImporters = null;
        Party jetpackCarriers = null;
        ContainerState container = new ContainerState(5,
                2,
                2,
                "Jetpacks",
                jetpackImporters,
                jetpackCarriers );
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(owner,carrier);
    }
}
