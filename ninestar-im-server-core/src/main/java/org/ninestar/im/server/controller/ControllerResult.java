package org.ninestar.im.server.controller;

public class ControllerResult {
	public static enum ControllerResultState {
		OK(0, "成功"), NOT_EXIST(1, "不存在资源"), ERROR(2, "发生错误");
		public int state;
		public final String msg;

		private ControllerResultState(int state, String msg) {
			this.state = state;
			this.msg = msg;
		}

	}

	private ControllerMethod method;
	private Object result;
	private ControllerResultState state;
	private Throwable error;

	public static ControllerResult getNotExistResult() {
		ControllerResult controllerResult = new ControllerResult();
		controllerResult.state = ControllerResultState.NOT_EXIST;
		return controllerResult;
	}

	public static ControllerResult getOkResult(ControllerMethod method, Object result) {
		ControllerResult controllerResult = new ControllerResult(method, result, ControllerResultState.OK);
		return controllerResult;
	}

	public static ControllerResult getErrorResult(ControllerMethod method, Throwable error) {
		ControllerResult controllerResult = new ControllerResult(method, error);
		return controllerResult;
	}

	private ControllerResult() {

	}

	public ControllerResult(ControllerMethod method, Object result, ControllerResultState state) {
		super();
		this.method = method;
		this.result = result;
		this.state = state;
	}

	private ControllerResult(ControllerMethod method, Throwable error) {
		super();
		this.method = method;
		this.state = ControllerResultState.ERROR;
		this.error = error;
	}

	public Throwable getError() {
		return error;
	}

	public ControllerMethod getMethod() {
		return method;
	}

	public Object getResult() {
		return result;
	}

	public ControllerResultState getState() {
		return state;
	}

}
