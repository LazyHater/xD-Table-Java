package effects;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import matrix.Display;
import matrix.TouchScreen;

public class EffectManager {
	private ArrayList<Effect> effectArray;
	private JComboBox<Effect> effectComboBox;
	private int idx = 2;

	public EffectManager(Display display) {
		effectArray = new ArrayList<Effect>();
		effectArray.add(new Rainbow(display));
		effectArray.add(new MetaBalls(display, 2));
		effectArray.add(new KMeanEffect(display));
		effectArray.add(new BlackScreen(display));
	}

	public void nextFrame(TouchScreen touchScreen) {
		effectArray.get(idx).nextFrame(touchScreen);
	}

	public void nextEffect() {
		idx++;
		if(idx >= effectArray.size())
			idx = 0;
		
		if(effectComboBox != null)
			effectComboBox.setSelectedIndex(idx);
	}
	
	public void setEffect(int idx){
		assert idx >= 0 && idx < effectArray.size() : "index out of range in setEffect";
		this.idx = idx;
	
		if(effectComboBox != null)
			effectComboBox.setSelectedIndex(idx);
	}
	
	public void registerComboBox(JComboBox<Effect> combo){
		this.effectComboBox = combo;
		combo.removeAllItems();
		
		for (Effect effect : effectArray)
			combo.addItem(effect);
		
		combo.setSelectedIndex(idx);
		
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				idx = effectComboBox.getSelectedIndex();
			}
		});
	}
}
