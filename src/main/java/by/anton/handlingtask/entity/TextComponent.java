package by.anton.handlingtask.entity;

import org.apache.logging.log4j.util.PropertySource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TextComponent extends AbstractTextComponent {
    protected List<AbstractTextComponent> componentList = new ArrayList<>();
    private int size = 0;

    public TextComponent(TextComponentType componentType) {
        super(componentType);
    }

    @Override
    public String operation() {
        String delimiter = componentType.getDelimiter();
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (AbstractTextComponent component : componentList
                ) {
            stringArrayList.add(component.operation());
            stringArrayList.add(delimiter);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : stringArrayList
                ) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void add(AbstractTextComponent textComponent) {
        size++;
        componentList.add(textComponent);
    }

    @Override
    public void remove(AbstractTextComponent textComponent) {
        size--;
        componentList.remove(textComponent);
    }

    public AbstractTextComponent getComponent(int index) {
        return componentList.get(index);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public List<AbstractTextComponent> getComponentList() {
        return componentList;
    }
}
