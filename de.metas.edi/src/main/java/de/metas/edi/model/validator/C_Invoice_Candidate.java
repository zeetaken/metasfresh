package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IEDIInvoiceCandBL;
import de.metas.edi.model.I_C_Invoice_Candidate;
import de.metas.util.Services;

@Validator(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID })
	public void setIsEDIRecipient(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final I_C_BPartner billBPartner = invoiceCandidate.getBill_BPartner();

		if (billBPartner == null || billBPartner.getC_BPartner_ID() <= 0)
		{
			// nothing to do
			return;
		}

		// Make sure the IsEDIRecipient flag is up to date in the invoice candidate
		// It shall always be the same as in the bill BPartner
		final de.metas.edi.model.I_C_BPartner ediPartner = InterfaceWrapperHelper.create(billBPartner, de.metas.edi.model.I_C_BPartner.class);

		final boolean isEDIRecipient = ediPartner.isEdiRecipient();
		invoiceCandidate.setIsEdiRecipient(isEDIRecipient);

	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW},
			ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, I_C_Invoice_Candidate.COLUMNNAME_M_InOut_ID })
	public void setIsEDIEnabled(final I_C_Invoice_Candidate invoiceCandidate)
	{
		Services.get(IEDIInvoiceCandBL.class).setEdiEnabled(invoiceCandidate);
	}
}
