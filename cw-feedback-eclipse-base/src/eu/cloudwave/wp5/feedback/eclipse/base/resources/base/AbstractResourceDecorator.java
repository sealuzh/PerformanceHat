package eu.cloudwave.wp5.feedback.eclipse.base.resources.base;

import org.eclipse.core.resources.IResource;

/**
 * An abstract base implementation for {@link IResource} decorators. Delegates all methods to the decorated
 * {@link IResource}.
 */
public class AbstractResourceDecorator extends AbstractBaseResourceDecorator implements IResource {

  protected final IResource resource;

  public AbstractResourceDecorator(final IResource resource) {
    this.resource = resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IResource resource() {
    return resource;
  }

}
