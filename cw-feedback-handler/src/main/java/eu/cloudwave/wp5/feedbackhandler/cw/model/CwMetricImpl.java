package eu.cloudwave.wp5.feedbackhandler.cw.model;

/**
 * Implementation of {@link CwMetric}.
 */
public class CwMetricImpl implements CwMetric {

  private String name;
  private String data;
  private String unit;
  private Number value;

  private CwMetricImpl(final String name, final String data, final String unit, final Number value) {
    this.name = name;
    this.data = data;
    this.unit = unit;
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getData() {
    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUnit() {
    return unit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getValue() {
    return value;
  }

  public static CwMetric of(final String name, final String data, final String unit, final Number value) {
    return new CwMetricImpl(name, data, unit, value);
  }

}
