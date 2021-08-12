package cbx.ubl

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Id(
    val idContent: String,
    val idSchemeIdentifier: String? = "FreeText",
    val idAgencyIdentifier: String? = null
)

data class Code(
    val codeContent: String,
    val codeListIdentifier: String? = "FreeText",
    val codeListAgencyIdentifier: String? = null
)

data class Contact(
    val email: String? = null,
    val telefax: String? = null,
    val telephone: String
)

data class Contract(
    val id: Id,
    val contractType: String? = "FreeText"
)

data class Address(
    val buildingNumber: String,
    val streetName: String,
    val cityName: String? = null,
    val countrySubEntity: String? = null,
    val country: String? = null,
    val postalZone: String? = null,
    val additionalStreetName: String? = null,
    val department: String? = null,
    val postbox: String? = null
)

data class Person(
    val firstName: String,
    val familyName: String? = null,
    val jobTitle: String? = null,
    val middleName: String? = null
)

data class PartyTaxScheme(
    val companyId: Id,
    val taxScheme: Id
)

data class PartyLegalEntity(
    val companyId: Id,
    val registrationAddress: Address? = null,
    val registrationName: String
)

data class EmbeddedDocumentBinaryObject(
    val binaryObjectContent: String,
    val binaryObjectMimeCode: String
)

data class Amount(
    val amountContent: Double,
    val amountCurrencyIdentifier: String? = CurrencyCode.USD.toString()
)

data class Attachment(
    val embeddedDocumentBinaryObject: EmbeddedDocumentBinaryObject? = null,
    val externalReference: String? = null //url
)

data class AdditionalProperty(
    val name: String,
    val value: String
)


data class Period(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val endDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val startDate: LocalDateTime
)

data class Quantity(
    val quantityContent: Int,
    val quantityUnitCode: String
)

data class Price(
    val allowanceCharge: Amount? = null,
    val baseQuantity: Quantity? = null,
    val priceAmount: Amount
)

data class Location(
    val address: Address,
    val id: Id? = null
)

data class Delivery(
    val deliveryLocation: Location,
    val deliveryPeriod: Period? = null,
    val deliveryParty: Party? = null,
    val deliveryTerms: List<DeliveryTerm>? = null
)

data class DeliveryTerm(
    val deliveryLocation: Location? = null,
    val id: Id? = null,
    val specialTerms: String
)

data class DocumentReference(
    val attachment: List<Attachment>? = null,
    val documentType: String? = null,
    val id: Id
)

data class TaxCategory(
    val id: Id?,
    val percent: Int,
    val taxScheme: Id?
)

data class TaxSubtotal(
    val taxAmount: Amount,
    val taxCategory: TaxCategory,
    val taxableAmount: Amount
)

data class TaxTotal(
    val taxAmount: Amount,
    val taxSubtotal: List<TaxSubtotal>? = listOf()
)

//

data class Party(
    val contact: List<Contact>? = null,
    val endpointId: List<Code>? = null,
    val partyIdentification: List<Id>,
    val partyLegalEntity: List<PartyLegalEntity>? = null,
    val partyName: String? = null,
    val partyTaxScheme: List<PartyTaxScheme>? = null,
    val person: List<Person>? = null,
    val postalAddress: List<Address>? = null
)

data class Item(
    val additionalItemProperty: List<AdditionalProperty>? = null,
    val classifiedTaxCategory: List<TaxCategory>? = null,
    val commodityClassification: List<Code>? = null,
    val description: String? = null,
    val name: String,
    val sellersItemIdentification: List<Id>? = null,
    val standardItemIdentification: List<Id>? = null
)

data class LineItem(
    val id: Id,
    val item: Item,
    val quantity: Quantity,
    val price: Price,
    val accountingCostCode: List<Code>? = null,
    val allowanceCharge: AllowanceCharge? = null,
    val originatorParty: Party? = null,
    val taxAmount: List<TaxSubtotal>? = null,
    val taxTotal: Amount? = null ,
    val note: String? = null,
    val lineExtensionAmount: Amount? = null,
    val delivery: List<Delivery>? = null,
    val partialDeliveryIndicator: Boolean? = false,
    val lineReference: List<DocumentReference>? = null,
)

data class PaymentMean(
    val payeeFinancialAccount: List<Id>?,
    val paymentChannelCode: String?,
    val paymentDueDate: String?,
    val paymentId: String,
    val paymentMeansCode: Code?
)

data class MonetaryTotal(
    val allowanceTotalAmount: Amount? = Amount(0.0),
    val chargeTotalAmount: Amount? = Amount(0.0),
    val lineExtensionAmount: Amount? = Amount(0.0),
    val payableAmount: Amount,
    val payableRoundingAmount: Amount? = Amount(0.0),
    val prepaidAmount: Amount? = Amount(0.0),
    val taxExclusiveAmount: Amount? = Amount(0.0),
    val taxInclusiveAmount: Amount? = Amount(0.0)
)

data class AllowanceCharge(
    val allowanceChargeReason: String? = null,
    val amount: Amount,
    val baseAmount: Amount? = Amount(amountContent = 0.0),
    val chargeIndicator: Boolean? = false,
    val multiplierFactorNumeric: Double? = 0.0
)


