import cbx.ubl.AdditionalProperty
import cbx.ubl.Address
import cbx.ubl.AllowanceCharge
import cbx.ubl.Amount
import cbx.ubl.Attachment
import cbx.ubl.Code
import cbx.ubl.Contact
import cbx.ubl.Contract
import cbx.ubl.CurrencyCode
import cbx.ubl.Delivery
import cbx.ubl.DeliveryTerm
import cbx.ubl.DocumentReference
import cbx.ubl.EmbeddedDocumentBinaryObject
import cbx.ubl.EnergyCostCode
import cbx.ubl.Id
import cbx.ubl.Item
import cbx.ubl.LineItem
import cbx.ubl.Location
import cbx.ubl.MonetaryTotal
import cbx.ubl.Order
import cbx.ubl.Party
import cbx.ubl.PartyIdSchemeIdentifier
import cbx.ubl.PartyLegalEntity
import cbx.ubl.PartyTaxScheme
import cbx.ubl.Period
import cbx.ubl.Person
import cbx.ubl.Price
import cbx.ubl.Quantity
import cbx.ubl.ReferenceSchemeIdentifier
import cbx.ubl.TaxTotal
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import org.junit.jupiter.api.Test

//import org.assertj.core.api.Assertions.assertThat
//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Test
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.json.JsonTest
//import org.springframework.boot.test.json.JacksonTester

//@JsonTest
class OrderTest() {
//    @Autowired
//    lateinit var jacksonTester: JacksonTester<Order>

    @Test
    fun `Regular Order as request`() {
        var order = Order(
            cbxVersionId = "0.1",
            orderNumber = "test order 01",
            issueDateTime = LocalDateTime.of(2021, 8,11, 11, 10, 42),
            note = "This is Order type unit test",
            documentCurrencyCode = CurrencyCode.USD,
            accountingCostCode = listOf(
                Code(
                    codeContent = "AFE1",
                    codeListAgencyIdentifier = EnergyCostCode.AFE.toString()
                )
            ),
            validityPeriod = Period(
                startDate = LocalDateTime.of(2021, Month.AUGUST, 2, 0, 0, 0, 0),
                endDate = LocalDateTime.of(2021, Month.NOVEMBER, 2, 0, 0, 0, 0)
            ),
            quotationDocumentReference = listOf(
                DocumentReference(
                    id = Id(
                        idContent = "test quotation 01",
                        idSchemeIdentifier = ReferenceSchemeIdentifier.CbxQuotationNumber.toString()
                    )
                ),
                DocumentReference(
                    id = Id(
                        idContent = "1",
                        idSchemeIdentifier = ReferenceSchemeIdentifier.CbxQuotationId.toString()
                    )
                )
            ),
            orderDocumentReference = listOf(DocumentReference(id = Id(idContent = "RejectedOrder123"))),
            originatorDocumentReference = listOf(DocumentReference(id = Id(idContent = "MAFO"))),
            additionalDocumentReference = listOf(
                DocumentReference(
                    id = Id(idContent = "Att01"), documentType = "timesheet", attachment = listOf(
                        Attachment(externalReference = "thhps://local.cbx.com/att/att01.pdf"),
                        Attachment(
                            EmbeddedDocumentBinaryObject(
                                binaryObjectMimeCode = "application/pdf",
                                binaryObjectContent = "UjBsR09EbGhjZ0dTQUxNQUFBUUNBRU1tQ1p0dU1GUXhEUzhi"
                            )
                        )
                    )
                )
            ),
            contract = listOf(Contract(id = Id(idContent = "test contract 01"))),
            buyerCustomerParty = Party(
                partyIdentification = listOf(
                    Id(
                        idContent = "party01", //todo: correct as cbx int id
                        idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString()
                    ),
                    Id(
                        idContent = "987456321",
                        idSchemeIdentifier = PartyIdSchemeIdentifier.DUNS.toString()
                    )
                ),
                partyName = "party01",
                postalAddress = listOf(
                    getAddress()
                ),
                partyTaxScheme = listOf(
                    PartyTaxScheme(
                        companyId = Id(idContent = "SE1234567801"),
                        taxScheme = Id(idContent = "AVT", idSchemeIdentifier = "UN/ECE 5153", idAgencyIdentifier = "6")
                    )
                ),
                partyLegalEntity = listOf(
                    PartyLegalEntity(
                        companyId = Id(
                            idContent = "5512895671",
                            idSchemeIdentifier = "Alberta Business Registry",
                            idAgencyIdentifier = "12"
                        ),
                        registrationName = "Alpine Service Inc.",
                        registrationAddress = getAddress()
                    )
                ),
                contact = listOf(Contact(telephone = "403-123-4567", email = "al@cbx.com")),
                person = listOf(Person(firstName = "tester")),
            ),
            sellerSupplierParty = Party(
                partyIdentification = listOf(
                    Id(
                        idContent = "party02", //todo: correct as cbx int id
                        idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString()
                    ),
                    Id(
                        idContent = "123456789",
                        idSchemeIdentifier = PartyIdSchemeIdentifier.DUNS.toString()
                    )
                ),
                partyName = "party02"
            ),
            delivery = listOf(
                Delivery(
                    deliveryLocation = Location(address = getAddress()),
                    deliveryParty = Party(
                        partyName = "Swedish trucking", partyIdentification = listOf(
                            Id(idContent = "Party03", idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString())
                        )
                    ),
                    deliveryTerms = listOf(DeliveryTerm(specialTerms = "FOT"))
                )
            ),
            allowanceCharge = listOf(
                AllowanceCharge(
                    allowanceChargeReason = "Transport documents",
                    Amount(amountContent = 100.00)
                )
            ),
            taxTotal = listOf(TaxTotal(taxAmount = Amount(100.00))),
            anticipatedMonetaryTotal = MonetaryTotal(
                lineExtensionAmount = Amount(amountContent = 6225.00),
                allowanceTotalAmount = Amount(amountContent = 100.00),
                chargeTotalAmount = Amount(amountContent = 100.00),
                payableAmount = Amount(amountContent = 6225.00)
            ),
            orderLine = listOf(
                LineItem(
                    id = Id(idContent = "1"),
                    item = Item(
                        name = "Hauling Service",
                        description = "ship water to fields",
                        additionalItemProperty = listOf(
                            AdditionalProperty(
                                name = "Service Type",
                                value = "Truck  Solvant"
                            )
                        )
                    ),
                    quantity = Quantity(quantityContent = 1, quantityUnitCode = "Hour"),
                    price = Price(priceAmount = Amount(amountContent = 50.00)),
                )
            )
        )

        val result = getMapper().writeValueAsString(order)

        assert(result.contains("test order 01"))

    }

    fun getAddress() = Address(
        buildingNumber = "123",
        streetName = "8 Ave SW",
        cityName = "Calgary",
        countrySubEntity = "Alberta",
        country = "Canada"
    )

    fun getMapper() = ObjectMapper()
        .registerModule(JavaTimeModule())
//        .registerModule(KotlinModule())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

//        .registerModule(JavaTimeModule())
//        .serializationInclusion(JsonInclude.Include.NON_NULL)
//        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        .addModule(KotlinModule()).build()

    fun getOrderJson() = """
        {
           "cbxVersionID":"0.1",
           "orderNumber":"test order 01",
           "issueDate":{
              "year":2021,
              "month":"AUGUST",
              "monthValue":8,
              "dayOfMonth":2,
              "chronology":{
                 "calendarType":"iso8601",
                 "id":"ISO"
              },
              "era":"CE",
              "dayOfYear":214,
              "dayOfWeek":"MONDAY",
              "leapYear":false
           },
           "issueTime":{
              "hour":15,
              "minute":24,
              "second":0,
              "nano":0
           },
           "documentCurrencyCode":"USD",
           "buyerCustomerParty":{
              "contact":[
                 {
                    "email":"al@cbx.com",
                    "telephone":"403-123-4567"
                 }
              ],
              "partyIdentification":[
                 {
                    "idSchemeIdentifier":"CBX",
                    "idContent":"party01"
                 },
                 {
                    "idSchemeIdentifier":"DUNS",
                    "idContent":"987456321"
                 }
              ],
              "partyLegalEntity":[
                 {
                    "companyID":{
                       "idSchemeIdentifier":"Alberta Business Registry",
                       "idAgencyIdentifier":"12",
                       "idContent":"5512895671"
                    },
                    "registrationAddress":{
                       "cityName":"Calgary",
                       "countrySubEntity":"Alberta",
                       "buildingNumber":"123",
                       "streetName":"8 Ave SW",
                       "country":"Canada"
                    },
                    "registrationName":"Alpine Service Inc."
                 }
              ],
              "partyName":"party01",
              "partyTaxScheme":[
                 {
                    "companyID":{
                       "idSchemeIdentifier":"FreeText",
                       "idContent":"SE1234567801"
                    },
                    "taxScheme":{
                       "idSchemeIdentifier":"UN/ECE 5153",
                       "idAgencyIdentifier":"6",
                       "idContent":"AVT"
                    }
                 }
              ],
              "person":[
                 {
                    "firstName":"tester"
                 }
              ],
              "postalAddress":[
                 {
                    "cityName":"Calgary",
                    "countrySubEntity":"Alberta",
                    "buildingNumber":"123",
                    "streetName":"8 Ave SW",
                    "country":"Canada"
                 }
              ]
           },
           "sellerSupplierParty":{
              "partyIdentification":[
                 {
                    "idSchemeIdentifier":"CBX",
                    "idContent":"party02"
                 },
                 {
                    "idSchemeIdentifier":"DUNS",
                    "idContent":"123456789"
                 }
              ],
              "partyName":"party02"
           },
           "orderLine":[
              {
                 "item":{
                    "name":"Hauling Service",
                    "additionalItemProperty":[
                       {
                          "name":"Service Type",
                          "value":"Truck  Solvant"
                       }
                    ],
                    "description":"ship water to fields"
                 },
                 "quantity":{
                    "quantityContent":1,
                    "quantityUnitCode":"Hour"
                 },
                 "price":{
                    "priceAmount":{
                       "amountContent":50.0,
                       "amountCurrencyIdentifier":"USD"
                    }
                 },
                 "partialDeliveryIndicator":false,
                 "id":{
                    "idSchemeIdentifier":"FreeText",
                    "idContent":"1"
                 }
              }
           ],
           "accountingCostCode":[
              {
                 "codeContent":"AFE1",
                 "codeListIdentifier":"FreeText",
                 "codeListAgencyIdentifier":"AFE"
              }
           ],
           "additionalDocumentReference":[
              {
                 "attachment":[
                    {
                       "externalReference":"thhps://local.cbx.com/att/att01.pdf"
                    },
                    {
                       "embeddedDocumentBinaryObject":{
                          "binaryObjectMimeCode":"application/pdf",
                          "binaryObjectContent":"UjBsR09EbGhjZ0dTQUxNQUFBUUNBRU1tQ1p0dU1GUXhEUzhi"
                       }
                    }
                 ],
                 "documentType":"timesheet",
                 "id":{
                    "idSchemeIdentifier":"FreeText",
                    "idContent":"Att01"
                 }
              }
           ],
           "allowanceCharge":[
              {
                 "allowanceChargeReason":"Transport documents",
                 "amount":{
                    "amountContent":100.0,
                    "amountCurrencyIdentifier":"USD"
                 },
                 "baseAmount":{
                    "amountContent":0.0,
                    "amountCurrencyIdentifier":"USD"
                 },
                 "chargeIndicator":false,
                 "multiplierFactorNumeric":0.0
              }
           ],
           "anticipatedMonetaryTotal":{
              "chargeTotalAmount":{
                 "amountContent":100.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "lineExtensionAmount":{
                 "amountContent":6225.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "payableAmount":{
                 "amountContent":6225.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "payableRoundingAmount":{
                 "amountContent":0.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "prepaidAmount":{
                 "amountContent":0.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "taxExclusiveAmount":{
                 "amountContent":0.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "taxInclusiveAmount":{
                 "amountContent":0.0,
                 "amountCurrencyIdentifier":"USD"
              },
              "allowanceTotalAmount":{
                 "amountContent":100.0,
                 "amountCurrencyIdentifier":"USD"
              }
           },
           "contract":[
              {
                 "contractType":"FreeText",
                 "id":{
                    "idSchemeIdentifier":"FreeText",
                    "idContent":"test contract 01"
                 }
              }
           ],
           "delivery":[
              {
                 "deliveryLocation":{
                    "address":{
                       "cityName":"Calgary",
                       "countrySubEntity":"Alberta",
                       "buildingNumber":"123",
                       "streetName":"8 Ave SW",
                       "country":"Canada"
                    }
                 },
                 "deliveryParty":{
                    "partyIdentification":[
                       {
                          "idSchemeIdentifier":"CBX",
                          "idContent":"Party03"
                       }
                    ],
                    "partyName":"Swedish trucking"
                 },
                 "deliveryTerms":[
                    {
                       "specialTerms":"FOT"
                    }
                 ]
              }
           ],
           "note":"This is Order type unit test",
           "orderDocumentReference":[
              {
                 "id":{
                    "idSchemeIdentifier":"FreeText",
                    "idContent":"RejectedOrder123"
                 }
              }
           ],
           "originatorDocumentReference":[
              {
                 "id":{
                    "idSchemeIdentifier":"FreeText",
                    "idContent":"MAFO"
                 }
              }
           ],
           "quotationDocumentReference":[
              {
                 "id":{
                    "idSchemeIdentifier":"CbxQuotationNumber",
                    "idContent":"test quotation 01"
                 }
              },
              {
                 "id":{
                    "idSchemeIdentifier":"CbxQuotationId",
                    "idContent":"1"
                 }
              }
           ],
           "taxTotal":[
              {
                 "taxAmount":{
                    "amountContent":100.0,
                    "amountCurrencyIdentifier":"USD"
                 },
                 "taxSubtotal":[
                    
                 ]
              }
           ],
           "validityPeriod":{
              "endDate":{
                 "year":2021,
                 "monthValue":11,
                 "dayOfMonth":2,
                 "hour":0,
                 "minute":0,
                 "second":0,
                 "nano":0,
                 "month":"NOVEMBER",
                 "dayOfYear":306,
                 "dayOfWeek":"TUESDAY",
                 "chronology":{
                    "calendarType":"iso8601",
                    "id":"ISO"
                 }
              },
              "startDate":{
                 "year":2021,
                 "monthValue":8,
                 "dayOfMonth":2,
                 "hour":0,
                 "minute":0,
                 "second":0,
                 "nano":0,
                 "month":"AUGUST",
                 "dayOfYear":214,
                 "dayOfWeek":"MONDAY",
                 "chronology":{
                    "calendarType":"iso8601",
                    "id":"ISO"
                 }
              }
           }
        }
    """.trimIndent()

    fun getOrderJsonIncudleNull() = """
        {
           "documentCurrencyCode":"USD",
           "buyerCustomerParty":{
              "contact":[
                 {
                    "email":"al@cbx.com",
                    "telefax":null,
                    "telephone":"403-123-4567"
                 }
              ],
              "endpointID":null,
              "partyIdentification":[
                 {
                    "idContent":"party01",
                    "idSchemeIdentifier":"CBX",
                    "idAgencyIdentifier":null
                 },
                 {
                    "idContent":"987456321",
                    "idSchemeIdentifier":"DUNS",
                    "idAgencyIdentifier":null
                 }
              ],
              "partyLegalEntity":[
                 {
                    "registrationAddress":{
                       "country":"Canada",
                       "buildingNumber":"123",
                       "streetName":"8 Ave SW",
                       "cityName":"Calgary",
                       "countrySubEntity":"Alberta",
                       "postalZone":null,
                       "additionalStreetName":null,
                       "department":null,
                       "postbox":null
                    },
                    "registrationName":"Alpine Service Inc.",
                    "companyID":{
                       "idContent":"5512895671",
                       "idSchemeIdentifier":"Alberta Business Registry",
                       "idAgencyIdentifier":"12"
                    }
                 }
              ],
              "partyName":"party01",
              "partyTaxScheme":[
                 {
                    "companyID":{
                       "idContent":"SE1234567801",
                       "idSchemeIdentifier":"FreeText",
                       "idAgencyIdentifier":null
                    },
                    "taxScheme":{
                       "idContent":"AVT",
                       "idSchemeIdentifier":"UN/ECE 5153",
                       "idAgencyIdentifier":"6"
                    }
                 }
              ],
              "person":[
                 {
                    "firstName":"tester",
                    "familyName":null,
                    "jobTitle":null,
                    "middleName":null
                 }
              ],
              "postalAddress":[
                 {
                    "country":"Canada",
                    "buildingNumber":"123",
                    "streetName":"8 Ave SW",
                    "cityName":"Calgary",
                    "countrySubEntity":"Alberta",
                    "postalZone":null,
                    "additionalStreetName":null,
                    "department":null,
                    "postbox":null
                 }
              ]
           },
           "sellerSupplierParty":{
              "contact":null,
              "endpointID":null,
              "partyIdentification":[
                 {
                    "idContent":"party02",
                    "idSchemeIdentifier":"CBX",
                    "idAgencyIdentifier":null
                 },
                 {
                    "idContent":"123456789",
                    "idSchemeIdentifier":"DUNS",
                    "idAgencyIdentifier":null
                 }
              ],
              "partyLegalEntity":null,
              "partyName":"party02",
              "partyTaxScheme":null,
              "person":null,
              "postalAddress":null
           },
           "orderLine":[
              {
                 "accountingCostCode":null,
                 "allowanceCharge":null,
                 "delivery":null,
                 "note":null,
                 "taxTotal":null,
                 "item":{
                    "name":"Hauling Service",
                    "description":"ship water to fields",
                    "classifiedTaxCategory":null,
                    "additionalItemProperty":[
                       {
                          "name":"Service Type",
                          "value":"Truck  Solvant"
                       }
                    ],
                    "commodityClassification":null,
                    "sellersItemIdentification":null,
                    "standardItemIdentification":null
                 },
                 "quantity":{
                    "quantityContent":1,
                    "quantityUnitCode":"Hour"
                 },
                 "price":{
                    "allowanceCharge":null,
                    "baseQuantity":null,
                    "priceAmount":{
                       "amountCurrencyIdentifier":"USD",
                       "amountContent":50.0
                    }
                 },
                 "originatorParty":null,
                 "partialDeliveryIndicator":false,
                 "lineReference":null,
                 "taxAmount":null,
                 "id":{
                    "idContent":"1",
                    "idSchemeIdentifier":"FreeText",
                    "idAgencyIdentifier":null
                 },
                 "lineExtensionAmount":null
              }
           ],
           "accountingCostCode":[
              {
                 "codeContent":"AFE1",
                 "codeListIdentifier":"FreeText",
                 "codeListAgencyIdentifier":"AFE"
              }
           ],
           "additionalDocumentReference":[
              {
                 "id":{
                    "idContent":"Att01",
                    "idSchemeIdentifier":"FreeText",
                    "idAgencyIdentifier":null
                 },
                 "attachment":[
                    {
                       "embeddedDocumentBinaryObject":null,
                       "externalReference":"thhps://local.cbx.com/att/att01.pdf"
                    },
                    {
                       "embeddedDocumentBinaryObject":{
                          "binaryObjectContent":"UjBsR09EbGhjZ0dTQUxNQUFBUUNBRU1tQ1p0dU1GUXhEUzhi",
                          "binaryObjectMimeCode":"application/pdf"
                       },
                       "externalReference":null
                    }
                 ],
                 "documentType":"timesheet"
              }
           ],
           "cbxVersionID":"0.1",
           "orderNumber":"test order 01",
           "issueDate":{
              "year":2021,
              "month":"AUGUST",
              "monthValue":8,
              "dayOfMonth":2,
              "chronology":{
                 "id":"ISO",
                 "calendarType":"iso8601"
              },
              "era":"CE",
              "dayOfYear":214,
              "dayOfWeek":"MONDAY",
              "leapYear":false
           },
           "issueTime":{
              "hour":15,
              "minute":24,
              "second":0,
              "nano":0
           },
           "allowanceCharge":[
              {
                 "amount":{
                    "amountCurrencyIdentifier":"USD",
                    "amountContent":100.0
                 },
                 "allowanceChargeReason":"Transport documents",
                 "baseAmount":{
                    "amountCurrencyIdentifier":"USD",
                    "amountContent":0.0
                 },
                 "chargeIndicator":false,
                 "multiplierFactorNumeric":0.0
              }
           ],
           "anticipatedMonetaryTotal":{
              "allowanceTotalAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":100.0
              },
              "chargeTotalAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":100.0
              },
              "lineExtensionAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":6225.0
              },
              "payableAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":6225.0
              },
              "payableRoundingAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":0.0
              },
              "prepaidAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":0.0
              },
              "taxExclusiveAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":0.0
              },
              "taxInclusiveAmount":{
                 "amountCurrencyIdentifier":"USD",
                 "amountContent":0.0
              }
           },
           "contract":[
              {
                 "contractType":"FreeText",
                 "id":{
                    "idContent":"test contract 01",
                    "idSchemeIdentifier":"FreeText",
                    "idAgencyIdentifier":null
                 }
              }
           ],
           "customizationID":null,
           "delivery":[
              {
                 "deliveryLocation":{
                    "address":{
                       "country":"Canada",
                       "buildingNumber":"123",
                       "streetName":"8 Ave SW",
                       "cityName":"Calgary",
                       "countrySubEntity":"Alberta",
                       "postalZone":null,
                       "additionalStreetName":null,
                       "department":null,
                       "postbox":null
                    },
                    "id":null
                 },
                 "deliveryPeriod":null,
                 "deliveryParty":{
                    "contact":null,
                    "endpointID":null,
                    "partyIdentification":[
                       {
                          "idContent":"Party03",
                          "idSchemeIdentifier":"CBX",
                          "idAgencyIdentifier":null
                       }
                    ],
                    "partyLegalEntity":null,
                    "partyName":"Swedish trucking",
                    "partyTaxScheme":null,
                    "person":null,
                    "postalAddress":null
                 },
                 "deliveryTerms":[
                    {
                       "id":null,
                       "specialTerms":"FOT",
                       "deliveryLocation":null
                    }
                 ]
              }
           ],
           "note":"This is Order type unit test",
           "orderDocumentReference":[
              {
                 "id":{
                    "idContent":"RejectedOrder123",
                    "idSchemeIdentifier":"FreeText",
                    "idAgencyIdentifier":null
                 },
                 "attachment":null,
                 "documentType":null
              }
           ],
           "originatorCustomerParty":null,
           "originatorDocumentReference":[
              {
                 "id":{
                    "idContent":"MAFO",
                    "idSchemeIdentifier":"FreeText",
                    "idAgencyIdentifier":null
                 },
                 "attachment":null,
                 "documentType":null
              }
           ],
           "quotationDocumentReference":[
              {
                 "id":{
                    "idContent":"test quotation 01",
                    "idSchemeIdentifier":"CbxQuotationNumber",
                    "idAgencyIdentifier":null
                 },
                 "attachment":null,
                 "documentType":null
              },
              {
                 "id":{
                    "idContent":"1",
                    "idSchemeIdentifier":"CbxQuotationId",
                    "idAgencyIdentifier":null
                 },
                 "attachment":null,
                 "documentType":null
              }
           ],
           "taxTotal":[
              {
                 "taxAmount":{
                    "amountCurrencyIdentifier":"USD",
                    "amountContent":100.0
                 },
                 "taxSubtotal":[
                    
                 ]
              }
           ],
           "validityPeriod":{
              "endDate":{
                 "nano":0,
                 "year":2021,
                 "monthValue":11,
                 "dayOfMonth":2,
                 "hour":0,
                 "minute":0,
                 "second":0,
                 "month":"NOVEMBER",
                 "dayOfYear":306,
                 "dayOfWeek":"TUESDAY",
                 "chronology":{
                    "id":"ISO",
                    "calendarType":"iso8601"
                 }
              },
              "startDate":{
                 "nano":0,
                 "year":2021,
                 "monthValue":8,
                 "dayOfMonth":2,
                 "hour":0,
                 "minute":0,
                 "second":0,
                 "month":"AUGUST",
                 "dayOfYear":214,
                 "dayOfWeek":"MONDAY",
                 "chronology":{
                    "id":"ISO",
                    "calendarType":"iso8601"
                 }
              }
           }
        }
    """.trimIndent()
}
