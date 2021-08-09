package org.openRealmOfStars.gui.labels;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see http://www.gnu.org/licenses/
*
*
* Customized and colorized Text Area
*
*/

public class InfoTextPane extends JTextPane {

  /**
   * Basic constructor with correct look and feel.
   */
  public InfoTextPane() {
    super();
    this.setFont(GuiStatics.getFontCubellanSmaller());
    this.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    this.setBackground(Color.BLACK);
    this.setBorder(new SimpleBorder());
    this.setSelectedTextColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setSelectionColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    this.setEditable(false);
  }

  /**
   * Add Text with certain color into text pane.
   * @param text Text to add
   * @param color Foreground color used.
   */
  public void addText(final String text, final Color color) {
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
        StyleConstants.Foreground, color);
    int len = getDocument().getLength();
    setCaretPosition(len);
    setCharacterAttributes(aset, false);
    replaceSelection(text);
  }
}
