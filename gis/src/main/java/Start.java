import de.cm.osm2po.routing.DefaultRouter;
import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;

import java.io.File;
import java.util.Properties;

public class Start {
    public static void main(String[] args) {
        main1(new String[]{"C:\\GIS\\osm2po-4.8.8\\moscow\\moscow_2po.gph"});
    }

    public static void main1(String[] args) {
        File graphFile = new File(args[0]);
        Graph graph = new Graph(graphFile);
        DefaultRouter router = new DefaultRouter();

        int sourceId = graph.findClosestVertexId(55.6080511f, 37.7186356f);
        int targetId = graph.findClosestVertexId(55.7073089f, 37.726036f);

        // Possible additional params for DefaultRouter
        Properties params = new Properties();
        params.setProperty("findShortestPath", "false");
        params.setProperty("ignoreRestrictions", "false");
        params.setProperty("ignoreOneWays", "false");
        params.setProperty("heuristicFactor", "1.0"); // 0.0 Dijkstra, 1.0 good A*

        int[] path = router.findPath(
                graph, sourceId, targetId, Float.MAX_VALUE, params);

        if (path != null) { // Found!
            for (int aPath : path) {
                RoutingResultSegment rrs = graph.lookupSegment(aPath);
                int segId = rrs.getId();
                int from = rrs.getSourceId();
                int to = rrs.getTargetId();
                String segName = rrs.getName().toString();
                System.out.println(from + "-" + to + "  " + segId + "/" + aPath + " " + segName);
            }
        }

        graph.close();
    }
}
