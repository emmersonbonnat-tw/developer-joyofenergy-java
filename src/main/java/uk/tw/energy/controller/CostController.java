package uk.tw.energy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.MeterReadingService;
import uk.tw.energy.service.PricePlanService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cost")
public class CostController {

  private final AccountService accountService;
  private final PricePlanService pricePlanService;

  public CostController(AccountService accountService, PricePlanService pricePlanService) {
    this.accountService = accountService;
    this.pricePlanService = pricePlanService;
  }

  @GetMapping("/last-week-usage/{smartMeterId}")
  public BigDecimal getLastWeekUsage(@PathVariable String smartMeterId) throws Exception {
    final String pricePlanId = accountService.getPricePlanIdForSmartMeterId(smartMeterId);
    if (pricePlanId == null) {
      throw new Exception("Price plan not found for smart meter");
    }
    return pricePlanService.getLastWeekConsumptionCostOfElectricityReadingsForAPricePlan(
        smartMeterId, pricePlanId);
  }
}
