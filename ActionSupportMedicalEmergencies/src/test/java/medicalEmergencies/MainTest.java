/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package medicalEmergencies;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import pojos.Person;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class MainTest {
    
    @Test
    public void testEmergencyResponse1() {
        Person p1 = new Person(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        executeRulesAndAssertProtocol(p1, "Call 112, start CPR and AED");
    }

    @Test
    public void testEmergencyResponse2() {
        Person p2 = new Person(false, false, false, false, false, false, false, false, false, false, false, false, false, false, true);
        executeRulesAndAssertProtocol(p2, "Turn off power source, do not cover the burnts, call 112, start CPR, use AED");
    }

    @Test
    public void testEmergencyResponse3() {
        Person p3 = new Person(false, false, false, false, false, false, false, false, false, false, true, false, false, false, false);
        executeRulesAndAssertProtocol(p3, "Call 112, do not move the person, start CPR, use AED");
    }

    @Test
    public void testEmergencyResponse4() {
        Person p4 = new Person(false, false, false, false, false, false, false, false, false, false, true, true, false, false, false);
        executeRulesAndAssertProtocol(p4, "Call 112, apply pressure to stop bleeding, do not move the person, start CPR, use AED");
    }

    @Test
    public void testEmergencyResponse5() {
        Person p5 = new Person(false, false, false, false, false, false, false, false, false, true, false, false, false, false, false);
        executeRulesAndAssertProtocol(p5, "Protect the person, call 112, do not move the person or remove the helmet, protect from cold or heat and do not give food or drinks");
    }

    @Test
    public void testEmergencyResponse6() {
        Person p6 = new Person(false, false, false, true, false, false, false, false, false, false, false, false, false, false, false);
        executeRulesAndAssertProtocol(p6, "Protect the person, call 112, do not move the person or remove the helmet, protect from cold or heat and do not give food or drinks");
    }

    @Test
    public void testEmergencyResponse7() {
        Person p7 = new Person(false, false, false, true, false, false, false, false, false, false, true, false, false, false, false);
        executeRulesAndAssertProtocol(p7, "Protect the person, call 112, do not move the person or remove the helmet, protect from cold or heat and do not give food or drinks, start CPR, use AED");
    }

    @Test
    public void testEmergencyResponse8() {
        Person p8 = new Person(false, false, false, true, false, false, false, false, false, false, true, true, false, false, false);
        executeRulesAndAssertProtocol(p8, "Protect the person, call 112, do not move the person or remove the helmet, protect from cold or heat and do not give food or drinks, start CPR and use AED");
    }
    
    @Test
    public void testEmergencyResponse9() {
        Person p9 = new Person(false, false, true, false, false, false, false, false, false, false, false, false, false, false, false);
        executeRulesAndAssertProtocol(p9, "Encourage the person to cough");
    }
    
    @Test
    public void testEmergencyResponse10() {
        Person p10 = new Person(false, false, true, false, false, false, false, false, false, false, false, false, true, false, false);
        executeRulesAndAssertProtocol(p10, "Do the Heimlich maneuver");
    }

    @Test
    public void testEmergencyResponse11() {
        Person p11 = new Person(false, false, true, false, false, false, false, false, false, false, true, false, false, false, false);
        executeRulesAndAssertProtocol(p11, "Call 112, look for medication");
    }

    @Test
    public void testEmergencyResponse12() {
        Person p12 = new Person(false, false, true, false, false, false, false, false, false, false, true, true, false, false, false);
        executeRulesAndAssertProtocol(p12, "Secure the area, put legs up, loosen tight clothing, call 112 if the person is not recovered after the previous stage");
    }

    @Test
    public void testEmergencyResponse13() {
        Person p13 = new Person(false, false, true, true, false, false, false, false, false, false, false, false, false, false, false);
        executeRulesAndAssertProtocol(p13, "Secure the area, put the person in recovery position, call 112 if the person is not recovered after the previous stage");
    }

    /**
    * La función executeRulesAndAssertProtocol es un método de ayuda en Java utilizado
    * para ejecutar reglas de Drools y verificar el protocolo resultante en pruebas unitarias.
    * Toma una instancia de la clase Person con síntomas específicos y el protocolo esperado como parámetros. 
    * El método crea una sesión de reglas(ksession), para esta sesión necesita el contenedor que a su vez
    * necesita el servicio si lo ves esta en orden. Finalmente con asserEquals se comprueba lo esperado frente a lo
    * que tenemos
    *
    * @param person          Instancia de la clase Person con síntomas específicos.
    * @param expectedProtocol Protocolo esperado que se compara con el protocolo resultante.
    */
    private static void executeRulesAndAssertProtocol(Person person, String expectedProtocol) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        
        KieSession ksession = kc.newKieSession("rulesSession");
 
        ksession.insert(person);
        ksession.fireAllRules();
        System.out.println(person);
        assertEquals(expectedProtocol, person.getProtocol());
        ksession.dispose();
    }
    
    //con métodos booleanos
    /*@Test
    public void testEmergencyResponse1Rule() {
        KieSession kSession = getKieSession();

        Person emergency = new Person("no", null, "no", "no", null, null, null, null, null, null, "no", "no", null, null);
        kSession.insert(emergency);
        kSession.fireAllRules();

        assertTrue(emergency.isCall112Printed()); 
        assertTrue(emergency.isStartCPRPrinted());
        assertTrue(emergency.isUseAEDPrinted());

        kSession.dispose();
    }

    @Test
    public void testEmergencyResponse2Rule() {
        KieSession kSession = getKieSession();

        Person emergency = new Person("no", null, "no", "no", null, null, null, null, null, "yes", "no", "no", null, null);
        kSession.insert(emergency);
        kSession.fireAllRules();

        assertTrue(emergency.isTurnOffPowerSourcePrinted());
        assertTrue(emergency.isDoNotCoverBurntsPrinted());
        assertTrue(emergency.isCall112Printed());
        assertTrue(emergency.isStartCPRPrinted());
        assertTrue(emergency.isUseAEDPrinted());

        kSession.dispose();
    }

    private KieSession getKieSession() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        return kContainer.newKieSession("ksession-rules");
    }*/
    
/*para versiones de Drools anteriores a Drools 6
    @Test
    public void medicalEmergency1Test() {
        KnowledgeBase base = createBase();
        StatefulKnowledgeSession session = base.newStatefulKnowledgeSession();
        MedicalEmergency medicalEmergency = new MedicalEmergency();
        medicalEmergency.setConscious("no");
        medicalEmergency.setBreathing("no");
        medicalEmergency.setBleeding("no");
        medicalEmergency.setElectric_shock("no");
        medicalEmergency.setMajor_trauma("no");
        medicalEmergency.setCar_accident("no");
        session.insert(medicalEmergency);
        session.fireAllRules(); //activa todas las reglas y te coge la que encuentra que es exacta a los datos metidos en el objeto "MedicalEmergency"
        session.dispose();
    }
    
    
    private KnowledgeBase createBase(){ //se supone que esto solo se debe hacer una vez y hay otras formas
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();//collection que va cargando los recursos y va compilando para luego meterlos en la base de conocimiento
        builder.add(new ClassPathResource("rules.drl"), ResourceType.DRL); //le especificamos la ruta (path) y le decimos de que tipo es (.DRL)
        
        if(builder.hasErrors()){ //si ha fallado, recorre lista de errores y nos dice en qué ha fallado
            for (KnowledgeBuilderError error : builder.getErrors()) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("No se logró compilar la base de conocimiento");
        }
        
        KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase(); //instancio esta base
        base.addKnowledgePackages(builder.getKnowledgePackages());//añade paquetes que KnowledgeBuilder compiló anteriormente
        
        return base;
    }
*/
}
