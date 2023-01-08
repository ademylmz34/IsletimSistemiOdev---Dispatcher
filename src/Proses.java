public class Proses {
    private final int prosesId;
    private String prosesDurumu;
    private int varisZamani;
    private int oncelikDegeri;
    private int prosesSuresi;
    private final String color;
    private int beklemeSuresi;

    public Proses(int prosesId, int varisZamani, int oncelikDegeri, int prosesSuresi, String color){
        this.prosesId = prosesId;
        this.varisZamani = varisZamani;
        this.oncelikDegeri = oncelikDegeri;
        this.prosesSuresi = prosesSuresi;
        this.color = color;
        this.prosesDurumu = "";
        this.beklemeSuresi=0;
    }

    public int getVarisZamani() {
        return varisZamani;
    }

    public String getColor() {
        return color;
    }

    public int getProsesId() {
        return prosesId;
    }

    public String getProsesDurumu() {
        return prosesDurumu;
    }

    public void setProsesDurumu(String prosesDurumu) {
        this.prosesDurumu = prosesDurumu;
    }

    public int getOncelikDegeri() {
        return oncelikDegeri;
    }

    public void setOncelikDegeri(int oncelikDegeri) {
        this.oncelikDegeri = oncelikDegeri;
    }

    public int getProsesSuresi() {
        return prosesSuresi;
    }

    public void setProsesSuresi(int prosesSuresi) {
        this.prosesSuresi = prosesSuresi;
    }

    public int getBeklemeSuresi() {
        return beklemeSuresi;
    }

    public void setBeklemeSuresi(int beklemeSuresi) {
        this.beklemeSuresi = beklemeSuresi;
    }
}

package cloudsim_round_robin;



        import java.util.List;

        import org.cloudbus.cloudsim.DatacenterBroker;
        import org.cloudbus.cloudsim.DatacenterCharacteristics;
        import org.cloudbus.cloudsim.Log;
        import org.cloudbus.cloudsim.Vm;
        import org.cloudbus.cloudsim.core.CloudSim;
        import org.cloudbus.cloudsim.core.CloudSimTags;
        import org.cloudbus.cloudsim.core.SimEvent;

/**
 * This broker allocates VMs to data centers following the
 * <a href="http://en.wikipedia.org/wiki/Round-robin_scheduling">Round-robin</a> algorithm.
 * @author alessandro
 */
public class RoundRobinDatacenterBroker extends DatacenterBroker
{

    /**
     * Creates an instance of this class associating to it a given name.
     * @param name The name to be associated to this broker. It might not be <code>null</code> or empty.
     * @throws Exception If the name contains spaces.
     */
    public RoundRobinDatacenterBroker(String name) throws Exception
    {
        super(name);
    }

    @Override
    protected void processResourceCharacteristics(SimEvent ev)
    {
        DatacenterCharacteristics characteristics = (DatacenterCharacteristics) ev.getData();
        getDatacenterCharacteristicsList().put(characteristics.getId(), characteristics);

        if (getDatacenterCharacteristicsList().size() == getDatacenterIdsList().size())
        {
            distributeRequestsForNewVmsAcrossDatacentersUsingTheRoundRobinApproach();
        }
    }

    /**
     * Distributes the VMs across the data centers using the round-robin approach. A VM is allocated to a data center only if there isn't
     * a VM in the data center with the same id.
     */
    protected void distributeRequestsForNewVmsAcrossDatacentersUsingTheRoundRobinApproach()
    {
        int numberOfVmsAllocated = 0;
        int i = 0;

        final List<Integer> availableDatacenters = getDatacenterIdsList();

        for (Vm vm : getVmList())
        {
            int datacenterId = availableDatacenters.get(i++ % availableDatacenters.size());
            String datacenterName = CloudSim.getEntityName(datacenterId);

            if (!getVmsToDatacentersMap().containsKey(vm.getId()))
            {
                Log.printLine(CloudSim.clock() + ": " + getName() + ": Trying to Create VM #" + vm.getId() + " in " + datacenterName);
                sendNow(datacenterId, CloudSimTags.VM_CREATE_ACK, vm);
                numberOfVmsAllocated++;
            }
        }

        setVmsRequested(numberOfVmsAllocated);
        setVmsAcks(0);
    }
}
