package de.metas.money;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.junit.Before;
import org.junit.Test;

import de.metas.lang.Percent;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MoneyServiceTest
{
	private MoneyService moneyService;

	private Currency currency;

	private Money zeroEuro;

	private Money seventyEuro;

	private Money oneHundretEuro;

	private Money twoHundredEuro;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		moneyService = new MoneyService(currencyRepository);

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		saveRecord(currencyRecord);

		final CurrencyId currencyId = CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID());
		currency = currencyRepository.getById(currencyId);

		zeroEuro = Money.of(0, currencyId);
		seventyEuro = Money.of(70, currencyId);
		oneHundretEuro = Money.of(100, currencyId);
		twoHundredEuro = Money.of(200, currencyId);
	}

	@Test
	public void percentage()
	{
		final Money result = moneyService.percentage(Percent.of(80), twoHundredEuro);

		assertThat(result.getCurrencyId()).isEqualTo(currency.getId());
		assertThat(result.getValue()).isEqualByComparingTo("160");
	}

	@Test
	public void percentage_zero()
	{
		final Money result = moneyService.percentage(Percent.of(0), twoHundredEuro);

		assertThat(result.getCurrencyId()).isEqualTo(currency.getId());
		assertThat(result).isEqualTo(zeroEuro);
		assertThat(result.isZero()).isTrue();
	}

	@Test
	public void subtractPercent()
	{
		assertThat(moneyService.subtractPercent(Percent.of(0), oneHundretEuro)).isSameAs(oneHundretEuro);

		assertThat(moneyService.subtractPercent(Percent.of(30), oneHundretEuro)).isEqualTo(seventyEuro);

		assertThat(moneyService.subtractPercent(Percent.of(55), zeroEuro)).isSameAs(zeroEuro);

	}
}
