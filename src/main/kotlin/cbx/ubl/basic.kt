package cbx.ubl

import java.time.LocalDateTime

data class Id(
    val IdContent: String,
    val IdSchemeIdentifier: String? = "FreeText",
    val IdAgencyIdentifier: String? = null
)

data class Code(
    val CodeContent: String,
    val CodeListIdentifier: String? = "FreeText",
    val CodeListAgencyIdentifier: String? = null
)

data class Contact(
    val Email: String? = null,
    val Telefax: String? = null,
    val Telephone: String
)

data class Contract(
    val ID: Id,
    val ContractType: String? = "FreeText"
)

data class Address(
    val BuildingNumber: String,
    val StreetName: String,
    val CityName: String? = null,
    val CountrySubEntity: String? = null,
    val Country: String? = null,
    val PostalZone: String? = null,
    val AdditionalStreetName: String? = null,
    val Department: String? = null,
    val Postbox: String? = null
)

data class Person(
    val FirstName: String,
    val FamilyName: String? = null,
    val JobTitle: String? = null,
    val MiddleName: String? = null
)

data class PartyTaxScheme(
    val CompanyID: Id,
    val TaxScheme: Id
)

data class PartyLegalEntity(
    val CompanyID: Id,
    val RegistrationAddress: Address? = null,
    val RegistrationName: String
)

data class EmbeddedDocumentBinaryObject(
    val BinaryObjectContent: String,
    val BinaryObjectMimeCode: String
)

data class Amount(
    val AmountContent: Double,
    val AmountCurrencyIdentifier: String? = CurrencyCode.USD.toString()
)

data class Attachment(
    val EmbeddedDocumentBinaryObject: EmbeddedDocumentBinaryObject? = null,
    val ExternalReference: String? = null //url
)

data class AdditionalProperty(
    val Name: String,
    val Value: String
)


data class Period(
    val EndDate: LocalDateTime,
    val StartDate: LocalDateTime
)

data class Quantity(
    val QuantityContent: Int,
    val QuantityUnitCode: String
)

data class Price(
    val AllowanceCharge: Amount? = null,
    val BaseQuantity: Quantity? = null,
    val PriceAmount: Amount
)

data class Location(
    val Address: Address,
    val ID: Id? = null
)

data class Delivery(
    val DeliveryLocation: Location,
    val DeliveryPeriod: Period? = null,
    val DeliveryParty: Party? = null,
    val DeliveryTerms: List<DeliveryTerm>? = null
)

data class DeliveryTerm(
    val DeliveryLocation: Location? = null,
    val ID: Id? = null,
    val SpecialTerms: String
)

data class DocumentReference(
    val Attachment: List<Attachment>? = null,
    val DocumentType: String? = null,
    val ID: Id
)

data class TaxCategory(
    val Id: Id?,
    val Percent: Int,
    val TaxScheme: Id?
)

data class TaxSubtotal(
    val TaxAmount: Amount,
    val TaxCategory: TaxCategory,
    val TaxableAmount: Amount
)

data class TaxTotal(
    val TaxAmount: Amount,
    val TaxSubtotal: List<TaxSubtotal>? = listOf()
)

//

data class Party(
    val Contact: List<Contact>? = null,
    val EndpointID: List<Code>? = null,
    val PartyIdentification: List<Id>,
    val PartyLegalEntity: List<PartyLegalEntity>? = null,
    val PartyName: String? = null,
    val PartyTaxScheme: List<PartyTaxScheme>? = null,
    val Person: List<Person>? = null,
    val PostalAddress: List<Address>? = null
)

data class Item(
    val AdditionalItemProperty: List<AdditionalProperty>? = null,
    val ClassifiedTaxCategory: List<TaxCategory>? = null,
    val CommodityClassification: List<Code>? = null,
    val Description: String? = null,
    val Name: String,
    val SellersItemIdentification: List<Id>? = null,
    val StandardItemIdentification: List<Id>? = null
)

data class LineItem(
    val ID: Id,
    val Item: Item,
    val Quantity: Quantity,
    val Price: Price,
    val AccountingCostCode: List<Code>? = null,
    val AllowanceCharge: AllowanceCharge? = null,
    val OriginatorParty: Party? = null,
    val TaxAmount: List<TaxSubtotal>? = null,
    val TaxTotal: Amount? = null ,
    val Note: String? = null,
    val LineExtensionAmount: Amount? = null,
    val Delivery: List<Delivery>? = null,
    val PartialDeliveryIndicator: Boolean? = false,
    val LineReference: List<DocumentReference>? = null,
)

data class PaymentMean(
    val PayeeFinancialAccount: List<Id>?,
    val PaymentChannelCode: String?,
    val PaymentDueDate: String?,
    val PaymentID: String,
    val PaymentMeansCode: Code?
)

data class MonetaryTotal(
    val AllowanceTotalAmount: Amount? = Amount(0.0),
    val ChargeTotalAmount: Amount? = Amount(0.0),
    val LineExtensionAmount: Amount? = Amount(0.0),
    val PayableAmount: Amount,
    val PayableRoundingAmount: Amount? = Amount(0.0),
    val PrepaidAmount: Amount? = Amount(0.0),
    val TaxExclusiveAmount: Amount? = Amount(0.0),
    val TaxInclusiveAmount: Amount? = Amount(0.0)
)

data class AllowanceCharge(
    val AllowanceChargeReason: String? = null,
    val Amount: Amount,
    val BaseAmount: Amount? = Amount(AmountContent = 0.0),
    val ChargeIndicator: Boolean? = false,
    val MultiplierFactorNumeric: Double? = 0.0
)


