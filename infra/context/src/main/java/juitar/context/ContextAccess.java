package juitar.context;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public class ContextAccess {

    private static final InheritableThreadLocal<Context> localContext = new InheritableThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new ContextImpl();
        }
    };

    public Context getContext() {
        return localContext.get();
    }
}
