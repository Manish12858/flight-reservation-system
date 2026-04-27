package com.manish.flightreservation.services;

import java.io.InputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.manish.flightreservation.dtos.ReservationRequest;
import com.manish.flightreservation.entities.Flight;
import com.manish.flightreservation.entities.Passenger;
import com.manish.flightreservation.entities.Reservation;
import com.manish.flightreservation.repos.FlightRepository;
import com.manish.flightreservation.repos.PassengerRepository;
import com.manish.flightreservation.repos.ReservationRepository;
import com.manish.flightreservation.util.EmailUtil;
import com.manish.flightreservation.util.PDFGenerator;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationServiceImp implements ReservationService{

    @Value("${com.manish.flightreservation.itinerary.dirpath}")
    public String PATH;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    PDFGenerator pdfGenerator;
    @Autowired
    EmailUtil emailUtil;

    private static final Logger LOGGER=  LoggerFactory.getLogger(ReservationServiceImp.class);

    @Override
    @Transactional
    public Reservation bookFlight(ReservationRequest request) {
        LOGGER.info("inside bookFlight()");

        Flight flight = flightRepository.findById(request.getFlightId()).get();
        LOGGER.info("Fetching flight for flightId: " + flight.getId());
        Passenger passenger=new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setEmail(request.getPassengerEmail());
        passenger.setPhone(request.getPassengerPhone());
        LOGGER.info("Saving passenger: " + passenger);
        Passenger savedPassenger= passengerRepository.save(passenger);

        Reservation reservation=new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        LOGGER.info("Saving reservation: " + reservation);
        Reservation savedReservation=reservationRepository.save(reservation);

        String filePath= PATH +savedReservation.getId()+".pdf";

        LOGGER.info("Generating the (itinerary) the booking pdf");
        pdfGenerator.generateIternary(savedReservation,filePath);
        LOGGER.info("Sending the Email of the booking with pdf");
        emailUtil.sendItineraryMail(passenger.getEmail(),filePath);

        return savedReservation;
    }

    @Override
    public void generateItinerary(int reservationId, HttpServletResponse response) {

        Reservation reservation = reservationRepository.findById(reservationId).get();

        try {

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // 🔥 LOGO
            InputStream is = getClass().getResourceAsStream("/static/images/logo.png");
            if (is != null) {
                Image logo = Image.getInstance(is.readAllBytes());
                logo.scaleAbsolute(80, 50);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            }

            document.add(new Paragraph(" "));

            // 🔥 TITLE
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Paragraph title = new Paragraph("FlyHigh Boarding Pass", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // 🔥 PNR
            Font pnrFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph pnr = new Paragraph("PNR: " + reservation.getId(), pnrFont);
            pnr.setAlignment(Element.ALIGN_CENTER);
            document.add(pnr);

            document.add(new Paragraph(" "));

            // 🔥 TABLE
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addRow(table, "Passenger Name", reservation.getPassenger().getFirstName());
            addRow(table, "Email", reservation.getPassenger().getEmail());
            addRow(table, "Flight Number", reservation.getFlight().getFlightNumber());
            addRow(table, "Airline", reservation.getFlight().getOperatingAirlines());
            addRow(table, "From", reservation.getFlight().getDepartureCity());
            addRow(table, "To", reservation.getFlight().getArrivalCity());

            document.add(table);

            document.add(new Paragraph(" "));

            // 🔥 QR CODE
            BarcodeQRCode qrCode = new BarcodeQRCode(
                    "PNR:" + reservation.getId(),
                    150, 150, null
            );

            Image qrImage = qrCode.getImage();
            qrImage.setAlignment(Image.ALIGN_CENTER);
            document.add(qrImage);

            document.add(new Paragraph(" "));

            // 🔥 FOOTER
            Paragraph footer = new Paragraph(
                    "Thank you for choosing FlyHigh ✈️\nHave a pleasant journey!",
                    new Font(Font.FontFamily.HELVETICA, 12)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 HELPER METHOD
    private void addRow(PdfPTable table, String key, String value) {

        Font font = new Font(Font.FontFamily.HELVETICA, 12);

        PdfPCell cell1 = new PdfPCell(new Phrase(key, font));
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell cell2 = new PdfPCell(new Phrase(value, font));

        table.addCell(cell1);
        table.addCell(cell2);
    }

}
