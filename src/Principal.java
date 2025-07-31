import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Principal {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Lista de monedas válidas (puede ampliarse)
        Set<String> monedasValidas = new HashSet<>(Arrays.asList(
                "USD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN",
                "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP",
                "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD",
                "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
                "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR",
                "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA",
                "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK",
                "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF",
                "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "SSP", "STN", "SYP", "SZL",
                "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS",
                "VES", "VND", "VUV", "WST", "XAF", "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
        ));

        System.out.println("\n+-+ Conversor de Monedas en Tiempo Real +-+");
        System.out.println("Listado de divisas: ARS, USD, BRL, EUR, CLP, UYU, PEN, MXN, y otras 155 monedas más.");
        System.out.println("Para salir, ingrese 0 como moneda de origen.\n");

        ConsultaMonedaApi consulta = new ConsultaMonedaApi();

        while (true) {
            System.out.print("Ingrese la moneda de ORIGEN (ej: USD, 0 para salir): ");
            String monedaOrigen = teclado.nextLine().trim().toUpperCase();

            if (monedaOrigen.equals("0")) {
                System.out.println("Programa finalizado. ¡Gracias por usar el conversor de monedas de Verónica Rebolleda!");
                break;
            }

            System.out.print("Ingrese la moneda DESTINO (ej: ARS): ");
            String monedaDestino = teclado.nextLine().trim().toUpperCase();

            // Validación sin llamar a la API
            boolean origenValido = monedasValidas.contains(monedaOrigen);
            boolean destinoValido = monedasValidas.contains(monedaDestino);

            if (!origenValido || !destinoValido) {
                if (!origenValido && !destinoValido) {
                    System.out.println("❌ Ambas monedas ingresadas son inválidas.\n");
                } else if (!origenValido) {
                    System.out.println("❌ La moneda de ORIGEN es inválida.\n");
                } else {
                    System.out.println("❌ La moneda de DESTINO es inválida.\n");
                }
                continue;
            }

            System.out.print("Ingrese el monto a convertir (solo números): ");
            double montoIngresado;
            try {
                montoIngresado = Double.parseDouble(teclado.nextLine());

                ApiRespuesta respuesta = consulta.consultar(monedaOrigen, monedaDestino);
                double resultado = montoIngresado * respuesta.conversion_rate;

                System.out.printf("✅ %.2f de %s son %.2f de %s\n\n", montoIngresado, monedaOrigen, resultado, monedaDestino);
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debe ingresar un monto válido para el monto.\n");
            } catch (RuntimeException e) {
                System.out.println("❌ Error al consultar la API: " + e.getMessage() + "\n");
            }
        }

        teclado.close();
    }
}
