package com.illucit.partyinvoice.immutabledata;

import org.junit.Assert;
import org.junit.Test;

public class AbstractImmutableHolderTest {

	@Test
	public void testImmutableHolder() {

		ImmutablePointHolder holder = new ImmutablePointHolder();
		Assert.assertEquals("(0,0)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());

		ImmutablePointHolder holderRedoSelf = holder.redo();
		Assert.assertEquals(holder, holderRedoSelf);
		ImmutablePointHolder holderUndoSelf = holder.redo();
		Assert.assertEquals(holder, holderUndoSelf);
	}

	@Test
	public void testImmutableHolderSupplier() {

		ImmutablePointHolder holder = new ImmutablePointHolder(-17, 23);
		Assert.assertEquals("(-17,23)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());
	}

	@Test
	public void testImmutableHolderOperation() {

		ImmutablePointHolder holder = new ImmutablePointHolder();
		Assert.assertEquals("(0,0)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());

		ImmutablePointHolder holder2 = holder.operate(new TranslationOperation(5, 6));

		Assert.assertNotEquals(holder, holder2);

		Assert.assertEquals("(0,0)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());

		Assert.assertEquals("(5,6)", holder2.getValue().toString());
		Assert.assertFalse(holder2.hasRedoStep());
		Assert.assertTrue(holder2.hasUndoStep());

		ImmutablePointHolder holder3 = holder2.operate(new TranslationOperation(-1, 9));

		Assert.assertNotEquals(holder, holder2);
		Assert.assertNotEquals(holder, holder3);
		Assert.assertNotEquals(holder2, holder3);

		Assert.assertEquals("(0,0)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());

		Assert.assertEquals("(5,6)", holder2.getValue().toString());
		Assert.assertFalse(holder2.hasRedoStep());
		Assert.assertTrue(holder2.hasUndoStep());

		Assert.assertEquals("(4,15)", holder3.getValue().toString());
		Assert.assertFalse(holder3.hasRedoStep());
		Assert.assertTrue(holder3.hasUndoStep());
	}

	@Test
	public void testImmutableHolderHistory() {

		ImmutablePointHolder holder = new ImmutablePointHolder();
		ImmutablePointHolder holder2 = holder.operate(new TranslationOperation(5, 6));
		ImmutablePointHolder holder3 = holder2.operate(new TranslationOperation(-1, 9));

		ImmutablePointHolder holder3Undo = holder3.undo();

		// While the holders are not equal, the undo value should be the one
		// from holder2
		Assert.assertNotEquals(holder2, holder3Undo);
		Assert.assertEquals(holder2.getValue(), holder3Undo.getValue());

		Assert.assertEquals("(5,6)", holder2.getValue().toString());
		Assert.assertFalse(holder2.hasRedoStep());
		Assert.assertTrue(holder2.hasUndoStep());

		Assert.assertEquals("(5,6)", holder3Undo.getValue().toString());
		Assert.assertTrue(holder3Undo.hasRedoStep());
		Assert.assertTrue(holder3Undo.hasUndoStep());

		ImmutablePointHolder holder3UndoUndo = holder3Undo.undo();
		ImmutablePointHolder holder2Undo = holder2.undo();

		Assert.assertNotEquals(holder, holder2Undo);
		Assert.assertEquals(holder.getValue(), holder2Undo.getValue());

		Assert.assertNotEquals(holder, holder3UndoUndo);
		Assert.assertEquals(holder.getValue(), holder3UndoUndo.getValue());

		Assert.assertNotEquals(holder2Undo, holder3UndoUndo);
		Assert.assertEquals(holder2Undo.getValue(), holder3UndoUndo.getValue());

		Assert.assertEquals("(0,0)", holder.getValue().toString());
		Assert.assertFalse(holder.hasRedoStep());
		Assert.assertFalse(holder.hasUndoStep());

		Assert.assertEquals("(0,0)", holder2Undo.getValue().toString());
		Assert.assertTrue(holder2Undo.hasRedoStep());
		Assert.assertFalse(holder2Undo.hasUndoStep());

		Assert.assertEquals("(0,0)", holder3UndoUndo.getValue().toString());
		Assert.assertTrue(holder3UndoUndo.hasRedoStep());
		Assert.assertFalse(holder3UndoUndo.hasUndoStep());

		ImmutablePointHolder holder3UndoUndoRedo = holder3UndoUndo.redo();
		ImmutablePointHolder holder2UndoRedo = holder2Undo.redo();

		// Now holder3UndoUndoRedo == holder3Undo as they are in the same chain
		Assert.assertEquals(holder3Undo, holder3UndoUndoRedo);
		Assert.assertEquals(holder3Undo.getValue(), holder3UndoUndoRedo.getValue());

		Assert.assertEquals(holder2, holder2UndoRedo);
		Assert.assertEquals(holder2.getValue(), holder2UndoRedo.getValue());

		Assert.assertEquals("(5,6)", holder2.getValue().toString());
		Assert.assertFalse(holder2.hasRedoStep());
		Assert.assertTrue(holder2.hasUndoStep());

		Assert.assertEquals("(5,6)", holder3Undo.getValue().toString());
		Assert.assertTrue(holder3Undo.hasRedoStep());
		Assert.assertTrue(holder3Undo.hasUndoStep());

		ImmutablePointHolder holder3UndoOp = holder3Undo.operate(new TranslationOperation(-1, 9));

		// Now neither the holders nor the values are equal as a new value
		// instance has been generated by the operation, although the operation
		// is the same as it was before

		Assert.assertNotEquals(holder3, holder3UndoOp);
		Assert.assertNotEquals(holder3.getValue(), holder3UndoOp.getValue());

		Assert.assertEquals("(4,15)", holder3.getValue().toString());
		Assert.assertFalse(holder3.hasRedoStep());
		Assert.assertTrue(holder3.hasUndoStep());

		Assert.assertEquals("(4,15)", holder3UndoOp.getValue().toString());
		Assert.assertFalse(holder3UndoOp.hasRedoStep());
		Assert.assertTrue(holder3UndoOp.hasUndoStep());

	}

}
