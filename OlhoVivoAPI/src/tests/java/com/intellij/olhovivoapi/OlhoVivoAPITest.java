package com.intellij.olhovivoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class OlhoVivoAPITest {

    private final String AUTHKEY = "3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3";
    private ObjectMapper jsonParser = OlhoVivoAPI.jsonParser;
    private OlhoVivoAPI api;

    @Before
    public void setUp() throws Exception {
        api = new OlhoVivoAPI(AUTHKEY);
        api.authenticate();
    }

    @Test
    public void testAuthenticate() {
        boolean resp = api.authenticate();
        Assert.assertTrue(resp);
    }

    @Test
    public void testSearchBusLines() throws Exception {
        BusLine[] busLines = api.searchBusLines("8000");

        String correct = "[{\"CodigoLinha\":1273,\"Circular\":false,\"Letreiro\":\"8000\",\"Sentido\":1,\"Tipo\":10,\"DenominacaoTPTS\":\"PCA.RAMOS DE AZEVEDO\",\"DenominacaoTSTP\":\"TERMINAL LAPA\",\"Informacoes\":null},{\"CodigoLinha\":34041,\"Circular\":false,\"Letreiro\":\"8000\",\"Sentido\":2,\"Tipo\":10,\"DenominacaoTPTS\":\"PCA.RAMOS DE AZEVEDO\",\"DenominacaoTSTP\":\"TERMINAL LAPA\",\"Informacoes\":null}]";
        Assert.assertEquals(correct, jsonParser.writeValueAsString(busLines));
    }

    @Test
    public void testGetBusLineDetails() throws Exception {
        String response = api.getBusLineDetails(1273);
        Assert.assertEquals("[]", response);
    }

    @Test
    public void testSearchBusStops() throws Exception {
        BusStop[] busStops = api.searchBusStops("afonso");

        String correct = "[{\"CodigoParada\":340015329,\"Nome\":\"AFONSO BRAZ B/C1\",\"Endereco\":\"R ARMINDA/ R BALTHAZAR DA VEIGA\",\"Latitude\":-23.592938,\"Longitude\":-46.672727},{\"CodigoParada\":340015328,\"Nome\":\"AFONSO BRAZ B/C2\",\"Endereco\":\"R ARMINDA/ R BALTHAZAR DA VEIGA\",\"Latitude\":-23.59337,\"Longitude\":-46.672766},{\"CodigoParada\":340015333,\"Nome\":\"AFONSO BRAZ C/B1\",\"Endereco\":\"R DOUTORA MARIA AUGUSTA SARAIVA/ R NATIVIDADE\",\"Latitude\":-23.59572,\"Longitude\":-46.673285},{\"CodigoParada\":340015331,\"Nome\":\"AFONSO BRAZ C/B2\",\"Endereco\":\"R DOUTORA MARIA AUGUSTA SARAIVA/ R NATIVIDADE\",\"Latitude\":-23.595087,\"Longitude\":-46.673152}]";
        Assert.assertEquals(correct, jsonParser.writeValueAsString(busStops));
    }

    @Test
    public void testSearchBusStopsByLine() throws Exception {
        BusStop[] busStops = api.searchBusStopsByLine(34041);

        String correct = "[{\"CodigoParada\":7014416,\"Nome\":\"ANGELICA C/B\",\"Endereco\":\"R DR.  ALBUQUERQUE LINS\",\"Latitude\":-23.534368,\"Longitude\":-46.654267},{\"CodigoParada\":60014604,\"Nome\":\"SESC POMPEIA C/B\",\"Endereco\":\"VDTO POMPEIA/ R JOAQUIM FERREIRA\",\"Latitude\":-23.525262,\"Longitude\":-46.682254},{\"CodigoParada\":60016783,\"Nome\":\"PARADA PALMEIRAS C/B\",\"Endereco\":\"R PADRE ANTONIO TOMAS/ AV POMPEIA\",\"Latitude\":-23.525653,\"Longitude\":-46.679493},{\"CodigoParada\":60016785,\"Nome\":\"ANTARTICA C/B\",\"Endereco\":\" R ENGENHEIRO STEVENSON/ PC SOUSA ARANHA \",\"Latitude\":-23.52622,\"Longitude\":-46.674187},{\"CodigoParada\":60016787,\"Nome\":\"BABY BARIONY C/B\",\"Endereco\":\" R DOUTOR COSTA JUNIOR/ R DONA GERMAINE BUCHARD \",\"Latitude\":-23.527611,\"Longitude\":-46.671389},{\"CodigoParada\":60016789,\"Nome\":\"PQ. DA AGUA BRANCA C/B\",\"Endereco\":\" R DONA GERMAINE BUCHARD/ R MINISTRO GODOI \",\"Latitude\":-23.529623,\"Longitude\":-46.667768},{\"CodigoParada\":480014608,\"Nome\":\"TIBERIO C/B\",\"Endereco\":\"R TIBERIO/ R MENFIS\",\"Latitude\":-23.522875,\"Longitude\":-46.688219},{\"CodigoParada\":480014609,\"Nome\":\"DUILIO C/B\",\"Endereco\":\"R DUILIO/ R CLAUDIO\",\"Latitude\":-23.522627,\"Longitude\":-46.692121},{\"CodigoParada\":480014610,\"Nome\":\"TENDAL DA LAPA C/B\",\"Endereco\":\"R ESPARTACO/ R MORAIS E SILVA\",\"Latitude\":-23.522339,\"Longitude\":-46.696246},{\"CodigoParada\":700006085,\"Nome\":\"ROSA E SILVA C/B\",\"Endereco\":\"R OLIMPIA DE ALMEIDA PRADO/ R LOPES DE OLIVEIRA\",\"Latitude\":-23.532609,\"Longitude\":-46.657451},{\"CodigoParada\":700016624,\"Nome\":\"ANA CINTRA C/B\",\"Endereco\":\"R ANA CINTRA\",\"Latitude\":-23.538419,\"Longitude\":-46.647364},{\"CodigoParada\":700016792,\"Nome\":\"PACAEMBU C/B\",\"Endereco\":\"AV PACAEMBU/ R TUPI\",\"Latitude\":-23.531864,\"Longitude\":-46.660859},{\"CodigoParada\":700016865,\"Nome\":\"NOTHMANN C/B\",\"Endereco\":\"R GENERAL JULIO MARCONDES SALGADO/ AL NOTHMANN\",\"Latitude\":-23.536466,\"Longitude\":-46.650565}]";
        Assert.assertEquals(correct, jsonParser.writeValueAsString(busStops));
    }

    @Test
    public void testSearchBusStopsByCorridor() throws Exception {
        BusStop[] busStops = api.searchBusStopsByCorridor(8);

        String correct = "[{\"CodigoParada\":260016859,\"Nome\":\"ANTONIA DE QUEIROS B/C\",\"Endereco\":\"R DONA ANTONIA DE QUEIROS/ R PIAUI\",\"Latitude\":-23.549577,\"Longitude\":-46.653216},{\"CodigoParada\":260016858,\"Nome\":\"ANTONIA DE QUEIROS C/B\",\"Endereco\":\"R DONA ANTONIA DE QUEIROS/ R PIAUI\",\"Latitude\":-23.54958,\"Longitude\":-46.65343},{\"CodigoParada\":630015008,\"Nome\":\"BRASIL B/C\",\"Endereco\":\" PC PORTUGAL/ R CONEGO EUGENIO LEITE \",\"Latitude\":-23.564642,\"Longitude\":-46.678602},{\"CodigoParada\":440015003,\"Nome\":\"BRASIL C/B\",\"Endereco\":\"R HENRIQUE SCHAUMANN/ R LISBOA\",\"Latitude\":-23.564096,\"Longitude\":-46.677671},{\"CodigoParada\":260016861,\"Nome\":\"CAIO PRADO B/C\",\"Endereco\":\"R MARQUES DE PARANAGUA/ R MARIA ANTONIA\",\"Latitude\":-23.548174,\"Longitude\":-46.649565},{\"CodigoParada\":260016860,\"Nome\":\"CAIO PRADO C/B\",\"Endereco\":\"R MARQUES DE PARANAGUA/ R MARIA ANTONIA\",\"Latitude\":-23.548312,\"Longitude\":-46.650406},{\"CodigoParada\":630015009,\"Nome\":\"CAP. ANTONIO ROSA B/C\",\"Endereco\":\"R DANTE CARRARO/ R CAPITAO ANTONIO ROSA\",\"Latitude\":-23.567915,\"Longitude\":-46.684758},{\"CodigoParada\":630015010,\"Nome\":\"CAP. ANTONIO ROSA C/B\",\"Endereco\":\" R TECAINDA/ R CAPITAO ANTONIO ROSA \",\"Latitude\":-23.567619,\"Longitude\":-46.684372},{\"CodigoParada\":440015006,\"Nome\":\"CLINICAS B/C\",\"Endereco\":\"R DOUTOR OVIDIO PIRES DE CAMPOS/ R MELO ALVES\",\"Latitude\":-23.558088,\"Longitude\":-46.667585},{\"CodigoParada\":440015007,\"Nome\":\"CLÍNICAS C/B\",\"Endereco\":\"R DOUTOR OVIDIO PIRES DE CAMPOS/ R MELO ALVES\",\"Latitude\":-23.558083,\"Longitude\":-46.667869},{\"CodigoParada\":120011360,\"Nome\":\"DRAUSIO B/C\",\"Endereco\":\"R MIRAGAIA/ R DRAUSIO\",\"Latitude\":-23.576878,\"Longitude\":-46.707716},{\"CodigoParada\":120011361,\"Nome\":\"DRAUSIO C/B\",\"Endereco\":\"R MIRAGAIA/ R DRAUSIO\",\"Latitude\":-23.577175,\"Longitude\":-46.707933},{\"CodigoParada\":960011372,\"Nome\":\"EDEN B/C\",\"Endereco\":\" R CENOBELINO SERRA/ R EDEN \",\"Latitude\":-23.588191,\"Longitude\":-46.727332},{\"CodigoParada\":960011373,\"Nome\":\"EDEN C/B\",\"Endereco\":\" R CENOBELINO SERRA/ R EDEN \",\"Latitude\":-23.588431,\"Longitude\":-46.727769},{\"CodigoParada\":630015016,\"Nome\":\"FARIA LIMA B/C\",\"Endereco\":\"cruzamento Av. Eusébio Matoso e Av. Brig. Faria Lima\",\"Latitude\":-23.571182,\"Longitude\":-46.691378},{\"CodigoParada\":630015011,\"Nome\":\"FARIA LIMA C/B\",\"Endereco\":\"cruzamento Av. Rebouças e Av. Brig. Faria Lima\",\"Latitude\":-23.570905,\"Longitude\":-46.690499},{\"CodigoParada\":550011364,\"Nome\":\"FUND CARLOS CHAGAS B/C\",\"Endereco\":\"R SANHARO/ R LADISLAU ROMAN\",\"Latitude\":-23.582836,\"Longitude\":-46.713849},{\"CodigoParada\":550011365,\"Nome\":\"FUND CARLOS CHAGAS C/B\",\"Endereco\":\"R GENERAL SENA VASCONCELOS/ R SANHARO\",\"Latitude\":-23.582898,\"Longitude\":-46.71419},{\"CodigoParada\":960011369,\"Nome\":\"JORGE JOAO SAAD B/C\",\"Endereco\":\"R DOUTOR ALEXANDRE MARCONDES FILHO/ AV DEPUTADO JACOB SALVADOR ZVEIBI\",\"Latitude\":-23.586951,\"Longitude\":-46.725216},{\"CodigoParada\":960011368,\"Nome\":\"JORGE JOAO SAAD C/B\",\"Endereco\":\"R ANGELO COLUCCI/ AV JORGE JOAO SAAD\",\"Latitude\":-23.586423,\"Longitude\":-46.723955},{\"CodigoParada\":440015004,\"Nome\":\"OSCAR FREIRE B/C\",\"Endereco\":\"R OSCAR FREIRE/ R CHABAD\",\"Latitude\":-23.560639,\"Longitude\":-46.672095},{\"CodigoParada\":440015005,\"Nome\":\"OSCAR FREIRE C/B\",\"Endereco\":\"R OSCAR FREIRE/ AL LORENA\",\"Latitude\":-23.560271,\"Longitude\":-46.671631},{\"CodigoParada\":630015015,\"Nome\":\"PARADA  ELDORADO  - B/C\",\"Endereco\":\"R JORGE RIZZO/ R SAO COLUMBANO\",\"Latitude\":-23.571709,\"Longitude\":-46.696577},{\"CodigoParada\":630015017,\"Nome\":\"PARADA 1 - ELDORADO C/B\",\"Endereco\":\"R JORGE RIZZO/ R SAO COLUMBANO\",\"Latitude\":-23.571622,\"Longitude\":-46.696564},{\"CodigoParada\":550011358,\"Nome\":\"PARADA VITAL BRASIL B/C\",\"Endereco\":\"R MMDC/ AV LINEU DE PAULA MACHADO\",\"Latitude\":-23.572987,\"Longitude\":-46.705031},{\"CodigoParada\":260015039,\"Nome\":\"PAULISTA B/C\",\"Endereco\":\"AV PAULISTA/ AV REBOUCAS\",\"Latitude\":-23.555883,\"Longitude\":-46.66306},{\"CodigoParada\":260016855,\"Nome\":\"PAULISTA C/B\",\"Endereco\":\"AV PAULISTA/ R ANTONIO CARLOS\",\"Latitude\":-23.555176,\"Longitude\":-46.66237},{\"CodigoParada\":260016857,\"Nome\":\"PEDRO TAQUES B/C\",\"Endereco\":\"R CORONEL JOSE EUSEBIO/ R PEDRO TAQUES\",\"Latitude\":-23.551843,\"Longitude\":-46.657399},{\"CodigoParada\":260016856,\"Nome\":\"PEDRO TAQUES C/B\",\"Endereco\":\"R CORONEL JOSE EUSEBIO/ R PEDRO TAQUES\",\"Latitude\":-23.551865,\"Longitude\":-46.657589},{\"CodigoParada\":550011362,\"Nome\":\"ROQUETE PINTO B/C\",\"Endereco\":\"R DOUTOR QUEIROS GUIMARAES/ R PORTO GRANDE\",\"Latitude\":-23.58095,\"Longitude\":-46.711425},{\"CodigoParada\":120011363,\"Nome\":\"ROQUETE PINTO C/B\",\"Endereco\":\"R DOUTOR QUEIROS GUIMARAES/ R PORTO GRANDE\",\"Latitude\":-23.581273,\"Longitude\":-46.711714},{\"CodigoParada\":120011366,\"Nome\":\"TRES IRMAOS B/C\",\"Endereco\":\"R JOSE JANNARELLI/ R TRES IRMAOS\",\"Latitude\":-23.584837,\"Longitude\":-46.71869},{\"CodigoParada\":120011367,\"Nome\":\"TRES IRMAOS C/B\",\"Endereco\":\"R JOSE JANNARELLI/ R TRES IRMAOS\",\"Latitude\":-23.584817,\"Longitude\":-46.719021}]";
        Assert.assertEquals(correct, jsonParser.writeValueAsString(busStops));
    }

    @Test
    public void testGetAllBusCorridors() throws Exception {
        BusCorridor[] busCorridors = api.getAllBusCorridors();

        String correct = "[{\"CodCot\":0,\"CodCorredor\":8,\"Nome\":\"Campo Limpo\"},{\"CodCot\":0,\"CodCorredor\":9,\"Nome\":\"Expresso Tiradentes\"},{\"CodCot\":0,\"CodCorredor\":3,\"Nome\":\"Inajar de Souza\"},{\"CodCot\":0,\"CodCorredor\":7,\"Nome\":\"Parelheiros\"},{\"CodCot\":0,\"CodCorredor\":1,\"Nome\":\"Pirituba\"},{\"CodCot\":0,\"CodCorredor\":2,\"Nome\":\"Santo Amaro\"},{\"CodCot\":0,\"CodCorredor\":10,\"Nome\":\"Paes de Barros\"}]";
        Assert.assertEquals(correct, jsonParser.writeValueAsString(busCorridors));
    }

    @Test
    public void testSearchBusPositionsByLine() throws Exception {
        BusLinePositions busLinePositions = api.searchBusPositionsByLine(34041);
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        assertThat(jsonParser.writeValueAsString(busLinePositions), containsString(currentTime));
    }

    @Test
    public void testGetForecastWithLineAndStop() throws Exception {
        ForecastWithStopAndLine forecast = api.getForecastWithStopAndLine(260015039, 1877);
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

        assertThat(jsonParser.writeValueAsString(forecast), containsString(currentTime));
    }

    @Test
    public void testGetForecastWithLine() throws Exception {
        ForecastWithLine forecast = api.getForecastWithLine(34041);
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        assertThat(jsonParser.writeValueAsString(forecast), containsString(currentTime));
    }

    @Test
    public void testGetForecastWithStop() throws Exception {
        ForecastWithStop forecast = api.getForecastWithStop(7014417);
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

        Assert.assertEquals(7014417, forecast.getBusStop().getCode());
        assertThat(jsonParser.writeValueAsString(forecast), containsString(currentTime));
    }
}